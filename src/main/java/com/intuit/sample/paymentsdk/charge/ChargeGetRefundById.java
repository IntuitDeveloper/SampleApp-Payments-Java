package com.intuit.sample.paymentsdk.charge;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Refund;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ChargeService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to get refund by id
 * 
 * @author dderose
 *
 */
public class ChargeGetRefundById {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ChargeGetRefundById.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		getChargeRefund(requestContext);
		
	}

	private static void getChargeRefund(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);	
		Refund refund;
		
		try {
			refund = chargeService.getRefund("add charge id", "add refund id");
			LOG.info("getIntuit_tid:::" + refund.getIntuit_tid());
			LOG.info("refund id:::" + refund.getId() + " " + refund.getStatus().toString());
			
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
