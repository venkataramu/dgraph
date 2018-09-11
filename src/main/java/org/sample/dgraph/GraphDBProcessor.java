package org.sample.dgraph;

import io.dgraph.DgraphClient;
import io.dgraph.DgraphProto.Mutation;
import io.dgraph.Transaction;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.sample.dgraph.model.PersonObject;
import org.sample.dgraph.model.Vertex;
import org.sample.dgraph.operations.DgraphOperations;
import org.sample.dgraph.util.DgraphConnection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.ByteString;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class GraphDBProcessor {
	
	public static void main(String args[]) throws Exception {
		DgraphLoader graphLoader = new DgraphLoader();
		PersonObject persObject = new PersonObject("Dgraph Sample");
		Mutation mu  = null;
		Transaction txn = getGraphClient().newTransaction();
		try {
			// Drop Data Base data from  DGraph
			DgraphOperations dbOperations = new DgraphOperations();
			dbOperations.dropDataBase(getGraphClient(), false);
			
//			dbOperations.createSchema(getGraphClient());
			
			Vertex vertex1 = new Vertex("12345479", "DEVICE", "_:12345479");
			Vertex vertex2 = new Vertex("12345489", "DEVICE", "_:12345489");
			Type type = new TypeToken<List<Vertex>>() {}.getType();
			List<Vertex> verticesList = new ArrayList<>();
			verticesList.add(vertex1);
			verticesList.add(vertex2);
			
			String  gsonDataString = new Gson().toJson(verticesList, type);
			mu = Mutation.newBuilder().setSetJson(ByteString.copyFromUtf8(gsonDataString)).build();
			txn.mutate(mu);
			txn.commit();
			/*System.out.println("Entered to save data in to DGraph");
			Response response = graphLoader.getResponseByQuery(getGraphClient(), persObject.getName());
			Gson gson = new Gson();
			System.out.println( response.getJson().toStringUtf8().length());
			People ppl = new Gson().fromJson(response.getJson().toStringUtf8(), People.class);
			if( ppl.all.isEmpty()) {
				String json = gson.toJson(persObject);

		    	// Run mutation
		    	mu = Mutation.newBuilder().setSetJson(ByteString.copyFromUtf8(json.toString())).build();
		    	txn.mutate(mu);
		    	txn.commit();*/
		    	System.out.println("Commit transaction completed");
//			}
		} catch(Exception e) {
			System.out.println("Unable to save data into DGraph "+e);
		} finally {
			txn.discard();
			txn.close();
		}
		
	}
	
	public static DgraphClient getGraphClient() {
		return  DgraphConnection.getInstance().getGraph();
	}

}
