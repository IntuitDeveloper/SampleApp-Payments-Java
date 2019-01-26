package com.intuit.sample.paymentsdk.echeck;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.ECheck;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ECheckService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to get echeck by id
 * 
 * @author dderose
 *
 */
public class ECheckRetrieve {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ECheckRetrieve.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		retrieveECheck(requestContext);
	}

	private static void retrieveECheck(RequestContext requestContext) {
		
		ECheckService eCheckService = new ECheckService(requestContext);		
		ECheck eCheck;
		
		try {
			eCheck = eCheckService.retrieve("add echeck id");
			LOG.info("getIntuit_tid:::" + eCheck.getIntuit_tid());
			LOG.info("echeck id:::" + eCheck.getId() + " " + eCheck.getStatus().toString());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
