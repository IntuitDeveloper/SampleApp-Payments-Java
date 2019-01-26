package com.intuit.sample.paymentsdk.charge;

import java.math.BigDecimal;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Capture;
import com.intuit.payment.data.Charge;
import com.intuit.payment.data.PaymentContext;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ChargeService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to capture charge
 * 
 * @author dderose
 *
 */
public class ChargeCapture {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ChargeCapture.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		captureCharge(requestContext);
		
	}

	private static void captureCharge(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);	
		
		PaymentContext context = new PaymentContext.Builder().mobile("false").isEcommerce("true").build();
		Capture capture = new Capture.Builder().amount(new BigDecimal("5.55")).context(context).build();	
		Charge charge;
		
		try {
			charge = chargeService.capture("add charge id", capture);
			LOG.info("getIntuit_tid:::" + charge.getIntuit_tid());
			LOG.info("charge id:::" + charge.getId() + " " + charge.getStatus().toString());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
