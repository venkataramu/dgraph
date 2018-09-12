package org.sample.dgraph;

import io.dgraph.DgraphClient;
import io.dgraph.DgraphProto.Response;
import io.dgraph.Transaction;

import java.util.Collections;
import java.util.Map;

import org.sample.dgraph.model.Vertex;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class DgraphLoader {
	
	public Response  getResponseByQuery(DgraphClient dGraphClient, String name) throws Exception {
		Transaction txn = dGraphClient.newTransaction();
		try {
			System.out.println("Query Prepared");
			String query = "query all($a: string){\n" + "all(func: eq(name, $a)) {\n" + "    name\n" + "  }\n" + "}";
			Map<String, String> vars = Collections.singletonMap("$a", name);
			Response response =  txn.queryWithVars(query, vars);
			System.out.println("Query Executed");
			return response;
		} catch(Exception e) {
			txn.discard();
			throw new Exception(e.getMessage(), e);
		} finally {
			txn.close();
		}
	}
	
	public String getVertex(DgraphClient dGraphClient, Vertex vertex) throws Exception {
		Transaction txn = dGraphClient.newTransaction();
		try {
			System.out.println("Get Vertex Query .... ");
			String query = "query vertexLoad($a: string){\n"+ "vertexLoad(func: eq(id, $a)) {\n" + "  id\n uid\n sourceType\n" +" }\n" + "}";
			Map<String, String> vars = Collections.singletonMap("$a", vertex.getId());
			Response response =  txn.queryWithVars(query, vars);
			response.getJson().toString();
		 /*Mutation mu =
		     Mutation.newBuilder().setSetNquads(ByteString.copyFromUtf8(String.format("<%s> <Ntopology> <0x7542> .", "0x7540"))).build();*/
					
		/*Mutation mu =
		   Mutation.newBuilder().setSetNquads(ByteString.copyFromUtf8(String.format("_:%s <Ntopology> _:%s .",))).build();*/
			return null;
		} catch(Exception e) {
			System.out.println("Exception "+e);
			throw new Exception(e.getMessage(), e);
		} finally {
			txn.discard();
			txn.close();
		}
	}
	
}
