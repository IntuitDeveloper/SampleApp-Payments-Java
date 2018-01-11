package com.intuit.sample.payment.helper;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author dderose
 *
 */
public final class MapperHelper {
	
	private static final ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getInstance() {	
		return mapper;
	}

	//Prevent instantiation
	private MapperHelper() {}

}
