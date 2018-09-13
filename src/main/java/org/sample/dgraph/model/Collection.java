package org.sample.dgraph.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class Collection {
	private List<Capability> capabilities = new ArrayList<>();
	private String hostName;
	private String ipAddress;
	private String serialNumber;
	private String macAddress;
	public List<Capability> getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(List<Capability> capabilities) {
		this.capabilities = capabilities;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
}
