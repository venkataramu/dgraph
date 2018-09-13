package org.sample.dgraph.model;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class Links {
	private String srcIP;
	private String srcInt;
	private String tgtIP;
	private String tgtInt;
	private String srcHostname;
	private String tgtHostname;
	private String speed;
	private String vlan;
	private String protocol;
	private String tgtMgmtIP;
	private String srcMgmtIP;
	public String getSrcIP() {
		return srcIP;
	}
	public void setSrcIP(String srcIP) {
		this.srcIP = srcIP;
	}
	public String getSrcInt() {
		return srcInt;
	}
	public void setSrcInt(String srcInt) {
		this.srcInt = srcInt;
	}
	public String getTgtIP() {
		return tgtIP;
	}
	public void setTgtIP(String tgtIP) {
		this.tgtIP = tgtIP;
	}
	public String getTgtInt() {
		return tgtInt;
	}
	public void setTgtInt(String tgtInt) {
		this.tgtInt = tgtInt;
	}
	public String getSrcHostname() {
		return srcHostname;
	}
	public void setSrcHostname(String srcHostname) {
		this.srcHostname = srcHostname;
	}
	public String getTgtHostname() {
		return tgtHostname;
	}
	public void setTgtHostname(String tgtHostname) {
		this.tgtHostname = tgtHostname;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getVlan() {
		return vlan;
	}
	public void setVlan(String vlan) {
		this.vlan = vlan;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getTgtMgmtIP() {
		return tgtMgmtIP;
	}
	public void setTgtMgmtIP(String tgtMgmtIP) {
		this.tgtMgmtIP = tgtMgmtIP;
	}
	public String getSrcMgmtIP() {
		return srcMgmtIP;
	}
	public void setSrcMgmtIP(String srcMgmtIP) {
		this.srcMgmtIP = srcMgmtIP;
	}
}
