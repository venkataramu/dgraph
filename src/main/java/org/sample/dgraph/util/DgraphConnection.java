package org.sample.dgraph.util;

import io.dgraph.DgraphClient;
import io.dgraph.DgraphGrpc;
import io.dgraph.DgraphGrpc.DgraphStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class DgraphConnection {
	private static final String HOSTNAME = "localhost";
	private static final int PORT = 9080;
	DgraphClient graphClient  = null;
	
	private static DgraphConnection _instance = null;
	
	public DgraphConnection() {
		openConn();
	}
	
	private void openConn() {
		if(graphClient == null ) {
			synchronized (this) {
				try {
					ManagedChannel channel =  ManagedChannelBuilder.forAddress(HOSTNAME, PORT).usePlaintext(true).build();
					DgraphStub stub = DgraphGrpc.newStub(channel);
					graphClient = new DgraphClient(stub);
				} catch(Exception e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}
	
	public static DgraphConnection getInstance() {
		if(_instance == null) {
			_instance = new DgraphConnection();
		}
		return _instance;
	}
 	
	public DgraphClient getGraph() {
		if(graphClient == null) {
			openConn();
		}
		return graphClient;
	}
}
