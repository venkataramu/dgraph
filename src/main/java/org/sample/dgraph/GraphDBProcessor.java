package org.sample.dgraph;

import io.dgraph.DgraphClient;


import java.util.Scanner;


import org.sample.dgraph.util.DgraphConnection;
import org.sample.dgraph.util.JsonResponseProcessor;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class GraphDBProcessor {
	
	public static void main(String args[]) throws Exception {
		//Transaction txn = getGraphClient().newTransaction();
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Enter File name to process Json ::");
			String fileName = sc.next();
			JsonResponseProcessor responseProcessor = new JsonResponseProcessor();
			responseProcessor.processJsonMessage(fileName);
			
			// Drop Data Base data from  DGraph
			/*DgraphOperations dbOperations = new DgraphOperations();
			DgraphLoader dgraphLoader = new DgraphLoader();
			dbOperations.dropDataBase(getGraphClient(), false);
			
//			dbOperations.createSchema(getGraphClient());
			
			Vertex vertex = new Vertex("123454791", "DEVICE");
			String sourceUid = dgraphLoader.getVertex(getGraphClient(), vertex);
			if(sourceUid == null) {
				System.out.println("Creating Vertex ....");
				sourceUid = dbOperations.createVertex(txn, vertex);
			}
			
			Vertex vertex2 = new Vertex("123454792", "DEVICE");
			String destUid = dgraphLoader.getVertex(getGraphClient(), vertex2);
			if(destUid == null) {
				System.out.println("Creating Vertex ....");
				destUid = dbOperations.createVertex(txn, vertex2);
			}
			System.out.println("SourceUid : "+sourceUid+" , destination Uid :"+destUid);
			dbOperations.saveEdge(txn, sourceUid, destUid);
			txn.commit();*/
		    System.out.println("Commit transaction completed");
		} catch(Exception e) {
			System.out.println("Unable to save data into DGraph "+e);
		} finally {
			/*txn.discard();
			txn.close();*/
			sc.close();
		}
		
	}
	
	public static DgraphClient getGraphClient() {
		return  DgraphConnection.getInstance().getGraph();
	}

}
