package org.sample.dgraph.model;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class Edge {
	String relationName;

	public Edge(String relationName) {
		this.relationName = relationName;
	}
	
	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	

}
