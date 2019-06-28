package com.intuit.sample.paymentsdk.charge;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Address;
import com.intuit.payment.data.Card;
import com.intuit.payment.data.Charge;
import com.intuit.payment.data.PaymentContext;
import com.intuit.payment.data.Refund;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ChargeService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to create a charge and then void it
 * 
 * @author dderose
 *
 */
public class ChargeVoid {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ChargeVoid.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		
		Charge charge = createCharge(requestContext);
		
		//set new request id
		requestContext.setRequestId(UUID.randomUUID().toString().replace("-", ""));
		
		voidTransaction(requestContext, charge.getRequestId());
		
	}

	private static void voidTransaction(RequestContext requestContext, String chargeRequestId) {
		
		ChargeService chargeService = new ChargeService(requestContext);	
		
		Refund voidTxn;
		
		try {
			voidTxn = chargeService.voidTransaction(chargeRequestId);
			LOG.info("getIntuit_tid:::" + voidTxn.getIntuit_tid());
			LOG.info("void id:::" + voidTxn.getId() + " " + voidTxn.getStatus().toString());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
	private static Charge createCharge(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);
		
		Address address = new Address.Builder().region("CA")
				.postalCode("94086").streetAddress("1130 Kifer Rd")
				.city("Sunnyvale").country("US").build();
		
		Card card = new Card.Builder()
				.expYear("2020").expMonth("02")
				.address(address).name("emulate=0")
				.cvc("123").number("4111111111111111").build();
		
		PaymentContext context = new PaymentContext.Builder()
				.mobile("false").isEcommerce("true").build();
		
		Charge chargeRequest = new Charge.Builder()
				.amount(new BigDecimal("5.55")).card(card)
				.currency("USD").context(context).build();
		Charge charge = null;
		
		try {
			charge = chargeService.create(chargeRequest);
			LOG.info("getIntuit_tid:::" + charge.getIntuit_tid());
			LOG.info("charge id:::" + charge.getId());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
		return charge;
		
	}
	
}
