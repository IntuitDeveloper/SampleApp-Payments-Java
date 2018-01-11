package com.intuit.sample.payment.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.LoggerFactory;

/**
 * 
 * @author dderose
 *
 */
public class ResourceConfig {
	
	private ResourceConfig() {}

	private static Properties prop = new Properties();
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ResourceConfig.class);
	
	public static Properties getInstance() {	
		
		try {
			return loadProperties();
		} catch (IOException e) {
			LOG.error("Error loading properties", e.getCause());
		}
		return prop;
	}

	
	public static Properties loadProperties() throws IOException {
		 
		try {
			prop = new Properties();
			String propFileName = "config.properties";
 
			InputStream inputStream = ResourceConfig.class.getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			inputStream.close();
			return prop;
		} catch (Exception e) {
			LOG.error("Error during loadProperties", e.getCause());
		}
		return prop; 
	}
	
	

}
