package org.sample.dgraph;

import io.dgraph.DgraphClient;
import io.dgraph.Transaction;

import org.sample.dgraph.model.Vertex;
import org.sample.dgraph.operations.DgraphOperations;
import org.sample.dgraph.util.DgraphConnection;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class GraphDBProcessor {
	
	public static void main(String args[]) throws Exception {
		Transaction txn = getGraphClient().newTransaction();
		try {
			// Drop Data Base data from  DGraph
			DgraphOperations dbOperations = new DgraphOperations();
			dbOperations.dropDataBase(getGraphClient(), false);
			
//			dbOperations.createSchema(getGraphClient());
			
			Vertex vertex = new Vertex("123454791", "DEVICE");
			String sourceUid = dbOperations.createVertex(txn, vertex);
			
			Vertex vertex2 = new Vertex("123454792", "DEVICE");
			String destUid = dbOperations.createVertex(txn, vertex2);
			
			dbOperations.saveEdge(txn, sourceUid, destUid);
			txn.commit();
		    System.out.println("Commit transaction completed");
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
