package org.sample.dgraph.model;

import java.util.List;

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
	List<Vertex> all;
	
	public Vertex(String id, String sourceType, String uid) {
		this.id = id;
		this.sourceType = sourceType;
		this.uid = uid;
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
	
}
