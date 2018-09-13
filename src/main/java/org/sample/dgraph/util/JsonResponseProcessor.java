package org.sample.dgraph.util;

import java.io.File;
import java.io.FileInputStream;

import org.sample.dgraph.model.NetworkTopology;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Venkata Ramu Kandulapati
 * 
 */
public class JsonResponseProcessor {
	
	public void processJsonMessage(String fileName) throws Exception {
		try {
			FileInputStream fIn = null;
			File file = new File(fileName);
			fIn = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fIn.read(bytes);
			NetworkTopology response = processMessage(bytes);
			processJsonData(response);

		} catch(Exception e) {
			System.out.println("Exception ::: "+ e);
			throw e;
		}
	}
	
	public NetworkTopology processMessage(byte[] bytes) throws Exception {
		NetworkTopology response = null;
		if(bytes != null && bytes.length > 0) {
			String jsonData = new String(bytes, "UTF-8");
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return jsonMapper.readValue(jsonData, NetworkTopology.class);
		}
		return response;
	}
	
	public void processJsonData(NetworkTopology topology) throws Exception {
		if(topology != null) {
			if(topology.getL2links() != null) {

			}
		}
	}

}
