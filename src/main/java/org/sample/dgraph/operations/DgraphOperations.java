package org.sample.dgraph.operations;

import io.dgraph.DgraphClient;
import io.dgraph.DgraphProto.Operation;
import io.dgraph.Transaction;

import org.sample.dgraph.model.Vertex;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class DgraphOperations {
	
	public void insertData() {
		
	}
	
	public void dropDataBase(DgraphClient dgraphClient, boolean isDrop) {
		if(isDrop) {
			dgraphClient.alter(Operation.newBuilder().setDropAll(isDrop).build());
			System.out.println("Drop Schema Done .....");
		}
			
		
	}
	
	public void createSchema(DgraphClient dgraphClient) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("name: string @index(exact) . \n");
		buffer.append("id: string @index(exact) . \n");
		buffer.append("sourceType: string @index(exact) . \n");
		System.out.println(buffer.toString());
	    Operation op = Operation.newBuilder().setSchema(buffer.toString()).build();
	    dgraphClient.alter(op);
	    System.out.println("Create Schema Done ...");
	}
	
	
	public void saveEdges(DgraphClient dGraphClient, Vertex sourceVertex, Vertex destVertex) {
		Transaction txn = dGraphClient.newTransaction();
		try {
			System.out.println("Query Preparing of Edges....");
		} catch(Exception e) {
			System.out.println("Exception :: "+e);
		} finally {
			txn.discard();
			txn.close();
		}
	}
}
