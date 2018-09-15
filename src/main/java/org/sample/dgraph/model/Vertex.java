package org.sample.dgraph.model;


/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class Vertex {
	String id;
	String sourceType;
	String name;
	String ipAddress;
	String uid;
	
	public Vertex(String id, String sourceType, String ipAdd, String name) {
		this.id = id;
		this.sourceType = sourceType;
		this.uid = "_:"+id;
		this.ipAddress = ipAdd;
		this.name = name;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	
}
