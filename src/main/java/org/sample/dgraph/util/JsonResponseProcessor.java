package org.sample.dgraph.util;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sample.dgraph.DgraphLoader;
import org.sample.dgraph.GraphDBProcessor;
import org.sample.dgraph.UUIDUtil;
import org.sample.dgraph.model.Links;
import org.sample.dgraph.model.NetworkTopology;
import org.sample.dgraph.operations.DgraphOperations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dgraph.DgraphClient;
import io.dgraph.Transaction;
import io.dgraph.DgraphProto.Assigned;
import io.dgraph.DgraphProto.Mutation;
import org.sample.dgraph.model.Vertex;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.ByteString;


/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class JsonResponseProcessor {
	
	public void processJsonMessage(String fileName, long clientId) throws Exception {
		try {
			FileInputStream fIn = null;
			File file = new File(fileName);
			fIn = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fIn.read(bytes);
			NetworkTopology response = processMessage(bytes);
			processJsonData(response, clientId);

		} catch(Exception e) {
			System.out.println("Exception ::: "+ e);
			throw e;
		}
	}
	
	public NetworkTopology processMessage(byte[] bytes) throws Exception {
		NetworkTopology response = null;
		if(bytes != null && bytes.length > 0) {
			String jsonData = new String(bytes, "UTF-8");
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return jsonMapper.readValue(jsonData, NetworkTopology.class);
		}
		return response;
	}
	
	public void processJsonData(NetworkTopology topology, long clientId) throws Exception {
		if(topology != null) {
			if(topology.getL2links() != null) {
				System.out.println("process json initiated");
				DgraphLoader graphLoader = new DgraphLoader();
				//PersonObject persObject = new PersonObject("Dgraph Sample");
				
				DgraphClient dgraphClient = GraphDBProcessor.getGraphClient();
				Transaction txn = null;
				try {
					// Drop Data Base data from  DGraph
					DgraphOperations dbOperations = new DgraphOperations();
					dbOperations.dropDataBase(GraphDBProcessor.getGraphClient(), false);
					/*System.out.println("dropped database");*/
					//dbOperations.createSchema(GraphDBProcessor.getGraphClient());
					long startTime = new Date().getTime();
					System.out.println("schema created");
					DgraphOperations dgraphOp = new DgraphOperations();	
					for(Links link : topology.getL2links()) {
						txn = dgraphClient.newTransaction();
						long v1Id = getGraphIdByUniquenessOfOrphan(clientId, link.getSrcMgmtIP());
						long v2Id = getGraphIdByUniquenessOfOrphan(clientId, link.getTgtMgmtIP());
						
						String srcVertexUid = graphLoader.getVertex(dgraphClient, String.valueOf(v1Id));
						String tgtVertexUid = graphLoader.getVertex(dgraphClient, String.valueOf(v2Id));
						List<Vertex> verticesList = new ArrayList<Vertex>();
						if(srcVertexUid == null || tgtVertexUid == null) {
							if(srcVertexUid == null) {
								Vertex srcVertex = new Vertex(String.valueOf(v1Id), "DEVICE", link.getSrcMgmtIP(), link.getSrcHostname());				
								verticesList.add(srcVertex);				
							}
							if(tgtVertexUid == null) {
								Vertex tgtVertex = new Vertex(String.valueOf(v2Id), "DEVICE", link.getTgtMgmtIP(), link.getTgtHostname());
								verticesList.add(tgtVertex);				
							}
							Type type = new TypeToken<List<Vertex>>() {}.getType();
							System.out.println("Entered to save data in to DGraph");
							String  gsonDataString = new Gson().toJson(verticesList, type);
							Mutation mu = Mutation.newBuilder().setSetJson(ByteString.copyFromUtf8(gsonDataString)).build();
							Assigned assigned = txn.mutate(mu);

							if(srcVertexUid == null)
								srcVertexUid = assigned.getUidsMap().get(String.valueOf(v1Id));
							if(tgtVertexUid == null)
								tgtVertexUid = assigned.getUidsMap().get(String.valueOf(v2Id));
						}
						
						dgraphOp.saveEdge(txn, srcVertexUid,tgtVertexUid);
						retryMutate(txn);
					}
					
					System.out.println("Total time to save vertices and edges::"+ (new Date().getTime()-startTime));
				}				
				catch(Exception e) {
					System.out.println("Unable to save data into DGraph "+e);
					
				} finally {
					if(txn!=null) {
						txn.discard();
						txn.close();
					}
					
				}

				
				
			}
		}
	}
	
	 private static long getGraphIdByUniquenessOfOrphan(long clientId, String mgmtIp) {
			String seedStr = "CLIENT_" + clientId + "_" + mgmtIp;
			return UUIDUtil.generateUniqueLongValue(seedStr);
			
		}
	 
	 
	 public static void retryMutate(Transaction txn) throws Exception{
	    	Throwable lastError = null;
			int retries = 3;
			boolean success = false;
			
			while(retries > 0 && !success) {			
				retries--;
				try {
					 txn.commit();
					success = true;
				} catch (Throwable t) {
					lastError = t;
					System.out.println("Exception: "+ t.getMessage());
				}
				try {
					if(!success) {
						//Wait for 5 sec before attempting again
						System.out.println("Exception: thread sleep: 5sec");
						Thread.sleep(5000);
					}
				} catch(Throwable t) {
				}
			}
			if(!success && lastError != null) {
				System.out.println("Failed to process graph save request : " + lastError.getMessage());
				throw new Exception("Failed to save");
			}
	    }

}
