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
import com.intuit.payment.data.Token;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ChargeService;
import com.intuit.payment.services.TokenService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to create charge
 * 
 * @author dderose
 *
 */
public class ChargeCreate {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ChargeCreate.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		createCharge(requestContext);
		
		//set new request id
		requestContext.setRequestId(UUID.randomUUID().toString().replace("-", ""));
		createChargeFromToken(requestContext);
	}
	
	private static void createCharge(RequestContext requestContext) {
		
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
		Charge charge;
		
		try {
			charge = chargeService.create(chargeRequest);
			LOG.info("getIntuit_tid:::" + charge.getIntuit_tid());
			LOG.info("charge id:::" + charge.getId());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
		
	}
	
	private static void createChargeFromToken(RequestContext requestContext) {
		
		TokenService tokenService = new TokenService(requestContext);
		Address address = new Address.Builder().region("CA")
				.postalCode("94086").streetAddress("1130 Kifer Rd")
				.city("Sunnyvale").country("US").build();
		
		Card card = new Card.Builder()
				.expYear("2020").expMonth("02")
				.address(address).name("emulate=0")
				.cvc("123").number("4111111111111111").build();
				
		Token tokenRequest = new Token.Builder().card(card).build();
		Token token;
		Charge charge;
		
		try {
			token = tokenService.createToken(tokenRequest);
			LOG.info("getIntuit_tid:::" + token.getIntuit_tid());
			LOG.info("value:::" + token.getValue());
			
			PaymentContext context = new PaymentContext.Builder()
					.mobile("false").isEcommerce("true").build();
			
			Charge chargeRequest = new Charge.Builder()
					.amount(new BigDecimal("5.55")).token(token.getValue())
					.currency("USD").context(context).build();
			
			//set new request id
			requestContext.setRequestId(UUID.randomUUID().toString().replace("-", ""));
			ChargeService chargeService = new ChargeService(requestContext);
			charge = chargeService.create(chargeRequest);
			LOG.info("getIntuit_tid:::" + charge.getIntuit_tid());
			LOG.info("charge id:::" + charge.getId());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
		
	}
	
}
