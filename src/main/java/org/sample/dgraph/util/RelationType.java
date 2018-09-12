package org.sample.dgraph.util;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public enum RelationType {
	TOPOLOGY("topology");
	
	private String name;
	
	private RelationType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
