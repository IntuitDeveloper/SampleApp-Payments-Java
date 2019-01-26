package com.intuit.sample.paymentsdk.charge;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Charge;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ChargeService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to get charge by id
 * 
 * @author dderose
 *
 */
public class ChargeRetrieve {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ChargeRetrieve.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		retrieveCharge(requestContext);
	}

	private static void retrieveCharge(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);		
		Charge charge;
		
		try {
			charge = chargeService.retrieve("add charge id");
			LOG.info("getIntuit_tid:::" + charge.getIntuit_tid());
			LOG.info("charge id:::" + charge.getId() + " " + charge.getStatus().toString());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
