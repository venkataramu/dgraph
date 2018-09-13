package org.sample.dgraph;

import io.dgraph.DgraphClient;
import io.dgraph.DgraphProto.Response;
import io.dgraph.Transaction;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.sample.dgraph.model.Vertex;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
			Gson gson = new Gson();
			JsonElement jsonElement = gson.fromJson(response.getJson().toStringUtf8(), JsonElement.class);
			if(jsonElement != null) {
				JsonObject jsonObj = jsonElement.getAsJsonObject();
				JsonElement jsonElements = jsonObj.get("vertexLoad");
				JsonArray array = jsonElements.getAsJsonArray();
				Iterator iterator = array.iterator();
				while (iterator.hasNext()) {
					JsonElement initialVertex = (JsonElement) iterator.next();
					Vertex innerVertex = gson.fromJson(initialVertex, Vertex.class);
					return innerVertex.getUid();
				}
			}
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
