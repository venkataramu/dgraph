package org.sample.dgraph;

import io.dgraph.DgraphClient;
import io.dgraph.DgraphGrpc;
import io.dgraph.DgraphGrpc.DgraphStub;
import io.dgraph.DgraphProto.Mutation;
import io.dgraph.DgraphProto.Operation;
import io.dgraph.DgraphProto.Response;
import io.dgraph.Transaction;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class DgraphSampleConn {
	private static final String TEST_HOSTNAME = "172.26.24.131";
	private static final int TEST_PORT = 9080;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Entered to connectivity");
		ManagedChannel channel =
		        ManagedChannelBuilder.forAddress(TEST_HOSTNAME, TEST_PORT).usePlaintext(true).build();
		    DgraphStub stub = DgraphGrpc.newStub(channel);
		DgraphClient dgraphClient = new DgraphClient(stub);
		
		dgraphClient.alter(Operation.newBuilder().setDropAll(true).build());
		dgraphClient.newTransaction();
		
		System.out.println("Client Connection Established");
		
	    // Set schema
	    /*String schema = "name: string @index(exact) .";
	    Operation op = Operation.newBuilder().setSchema(schema).build();
	    
	    dgraphClient.alter(op);*/

	    Gson gson = new Gson(); // For JSON encode/decode

	    Transaction txn = dgraphClient.newTransaction();
	    Mutation mu  = null;
	    
	    try {
	    	// Create data
	    	Person p = new Person();
	    	p.name = "Alice";

	    	// Serialize it
	    	String json = gson.toJson(p);

	    	// Run mutation
	    	mu = Mutation.newBuilder().setSetJson(ByteString.copyFromUtf8(json.toString())).build();
	    	
	    	txn.mutate(mu);
	    	txn.commit();

	    }  catch(Exception e) {
	    	System.out.println("Exception :"+ e);
	    	retryTransaction(txn, mu);
	    } finally {
	    	System.out.println("finally block executed");
	    	txn.discard();
	    }
	    
	    String query =
	            "query all($a: string){\n" + "all(func: eq(name, $a)) {\n" + "    name\n" + "  }\n" + "}";
	        Map<String, String> vars = Collections.singletonMap("$a", "Alice");
	        Response res = dgraphClient.newTransaction().queryWithVars(query, vars);

	        // Deserialize
	        People ppl = gson.fromJson(res.getJson().toStringUtf8(), People.class);
	        
	        // Print results
	        System.out.printf("people found: %d\n", ppl.all.size());
	        ppl.all.forEach(person -> System.out.println(person.name));
	}
	
	static class Person {
	    String name;

	    Person() {}
	  }

	  static class People {
	    List<Person> all;

	    People() {}
	}
	  
	  public static void retryTransaction(Transaction txn, Mutation mu) {
		  int i=0;
		  while(i<3) {
			  try {
				  txn.mutate(mu);
				  System.out.println("Transaction commited");
				  txn.commit();
				  break;
			  } catch(Exception e) {
				  System.out.println("Exception "+e);
				  i++;
			  }
		  }
	  }

}
