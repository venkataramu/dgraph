package org.sample.dgraph;

import io.dgraph.DgraphClient;
import io.dgraph.DgraphProto.Response;
import io.dgraph.Transaction;

import java.util.Collections;
import java.util.Map;

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
	
}
