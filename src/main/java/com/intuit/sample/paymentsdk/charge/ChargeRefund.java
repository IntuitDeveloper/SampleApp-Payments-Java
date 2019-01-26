package com.intuit.sample.paymentsdk.charge;

import java.math.BigDecimal;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.PaymentContext;
import com.intuit.payment.data.Refund;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ChargeService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to create refund
 * 
 * @author dderose
 *
 */
public class ChargeRefund {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ChargeRefund.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		refundCharge(requestContext);
		
	}

	private static void refundCharge(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);	
		
		PaymentContext context = new PaymentContext.Builder().recurring(Boolean.FALSE).build();
		Refund refundRequest = new Refund.Builder().amount(new BigDecimal("5.55")).description("first refund").context(context).build();
		Refund refund;
		
		try {
			refund = chargeService.refund("add charge id", refundRequest);
			LOG.info("getIntuit_tid:::" + refund.getIntuit_tid());
			LOG.info("refund id:::" + refund.getId() + " " + refund.getStatus().toString());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
