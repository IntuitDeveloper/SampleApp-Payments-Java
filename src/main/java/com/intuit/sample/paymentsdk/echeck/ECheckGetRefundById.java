package com.intuit.sample.paymentsdk.echeck;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Refund;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ECheckService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to get refund by id
 * 
 * @author dderose
 *
 */
public class ECheckGetRefundById {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ECheckGetRefundById.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		getECheckRefund(requestContext);
	}

	private static void getECheckRefund(RequestContext requestContext) {
		
		ECheckService eCheckService = new ECheckService(requestContext);	
		Refund refund;
		
		try {
			refund = eCheckService.getRefund("add echeck id", "add refund id");
			LOG.info("getIntuit_tid:::" + refund.getIntuit_tid());
			LOG.info("echeck id:::" + refund.getId() + " " + refund.getStatus().toString());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
