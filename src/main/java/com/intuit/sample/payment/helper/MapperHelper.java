package com.intuit.sample.payment.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * @author dderose
 *
 */
public final class MapperHelper {
	
	private static final ObjectMapper mapper;
	
	public static final String TIMEZONE_UTC = "UTC";
	public static final String DATETIMEFORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	static {
		mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		DateFormat dateFormat = new SimpleDateFormat(DATETIMEFORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
		mapper.setDateFormat(dateFormat);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	public static ObjectMapper getInstance() {	
		return mapper;
	}

	//Prevent instantiation
	private MapperHelper() {}

}
