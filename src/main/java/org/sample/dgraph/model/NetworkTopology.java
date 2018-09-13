package org.sample.dgraph.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class NetworkTopology {
	private long profileId;
	private long clientId;
	private long timeStamp;
	private String responseType;
	private List<Collection> addedCollection = new ArrayList<>();
	private List<Collection> removedCollection = new ArrayList<>();
	private List<Links> l2links = new ArrayList<>();
	private List<Links> removedL2Links = new ArrayList<>();
	
	public long getProfileId() {
		return profileId;
	}
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public List<Collection> getAddedCollection() {
		return addedCollection;
	}
	public void setAddedCollection(List<Collection> addedCollection) {
		this.addedCollection = addedCollection;
	}
	public List<Collection> getRemovedCollection() {
		return removedCollection;
	}
	public void setRemovedCollection(List<Collection> removedCollection) {
		this.removedCollection = removedCollection;
	}
	public List<Links> getL2links() {
		return l2links;
	}
	public void setL2links(List<Links> l2links) {
		this.l2links = l2links;
	}
	public List<Links> getRemovedL2Links() {
		return removedL2Links;
	}
	public void setRemovedL2Links(List<Links> removedL2Links) {
		this.removedL2Links = removedL2Links;
	}

}
