/*
 * This computer program is the confidential information and proprietary trade
 * secret of VistaraIT, Inc. Possessions and use of this program must
 * conform strictly to the license agreement between the user and
 * VistaraIT, Inc., and receipt or possession does not convey any rights
 * to divulge, reproduce, or allow others to use this program without specific
 * written authorization of VistaraIT, Inc.
 * 
 * Copyright  2014 VistaraIT, Inc. All Rights Reserved.
*/
package org.sample.dgraph;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class UUIDUtil {
	
	private static final Logger log = LoggerFactory.getLogger(UUIDUtil.class);
	
	private UUIDUtil() {}
	
	public static String generateUUID(String seedString) {
		try {
			byte[] bytesOfMessage = seedString.getBytes("UTF-8");
	    	MessageDigest md = MessageDigest.getInstance("MD5");
	    	byte[] thedigest = md.digest(bytesOfMessage);
	        UUID uuid = UUID.nameUUIDFromBytes(thedigest);
	        return uuid.toString();
		} catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	public static long generateUniqueLongValue(String seedStr) {
		try {
			long val = -1;
			String uuidStr = generateUUID(seedStr);
			do {
				if(log.isDebugEnabled()) {
					log.debug(seedStr + "\t\t" + uuidStr );
				}
				final UUID uidObj = UUID.fromString(uuidStr);
				final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
				buffer.putLong(uidObj.getLeastSignificantBits());
				buffer.putLong(uidObj.getMostSignificantBits());
				final BigInteger bi = new BigInteger(buffer.array());
				val = bi.longValue();
				uuidStr = generateUUID(uuidStr);
			} while (val < 0);
			return val;
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}
}
