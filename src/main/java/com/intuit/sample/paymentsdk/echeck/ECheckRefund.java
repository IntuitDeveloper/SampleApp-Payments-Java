package com.intuit.sample.paymentsdk.echeck;

import java.math.BigDecimal;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Refund;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ECheckService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to create refund
 * 
 * @author dderose
 *
 */
public class ECheckRefund {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ECheckRefund.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		refundECheck(requestContext);
		
	}

	private static void refundECheck(RequestContext requestContext) {
		
		ECheckService eCheckService = new ECheckService(requestContext);	
		Refund refundRequest = new Refund();
		refundRequest.setAmount(new BigDecimal("2.22"));
		Refund refund;
		
		try {
			refund = eCheckService.refund("add echeck id", refundRequest);
			LOG.info("getIntuit_tid:::" + refund.getIntuit_tid());
			LOG.info("echeck id:::" + refund.getId() + " " + refund.getStatus().toString());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
