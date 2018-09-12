package org.sample.dgraph.operations;

import io.dgraph.DgraphClient;
import io.dgraph.DgraphProto.Assigned;
import io.dgraph.DgraphProto.Mutation;
import io.dgraph.DgraphProto.Operation;
import io.dgraph.Transaction;

import org.sample.dgraph.model.Vertex;
import org.sample.dgraph.util.RelationType;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;

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
	
	public String  createVertex(Transaction txn, Vertex vertex) {
		String  gsonVertex1String = new Gson().toJson(vertex, Vertex.class);
		Mutation mu1  = Mutation.newBuilder().setSetJson(ByteString.copyFromUtf8(gsonVertex1String)).build();
		Assigned assigned = txn.mutate(mu1);
		return assigned.getUidsMap().get(vertex.getId());
	}
	
	public void saveEdge(Transaction txn, String sourceUid, String destUid) {
		Mutation edgemu =
				Mutation.newBuilder().setSetNquads(ByteString.copyFromUtf8(String.format("<%s> <%s> <%s> .", sourceUid, RelationType.TOPOLOGY.getName(), destUid)))
					   .build();
		txn.mutate(edgemu);
	}
	
	public void saveEdges(DgraphClient dGraphClient, Vertex sourceVertex, Vertex destVertex) {
		Transaction txn = dGraphClient.newTransaction();
		try {
			System.out.println("Query Preparing of Edges....");
			Mutation mu =
				Mutation.newBuilder().setSetNquads(ByteString.copyFromUtf8(String.format("_:%s <Ntopology> _:%s .", sourceVertex.getId(), destVertex.getId())))
					   .build();
			txn.mutate(mu);
			txn.commit();
		} catch(Exception e) {
			System.out.println("Exception :: "+e);
		} finally {
			txn.discard();
			txn.close();
		}
	}
	
	
}
