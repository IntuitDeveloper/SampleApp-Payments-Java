package com.intuit.sample.paymentsdk.token;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Address;
import com.intuit.payment.data.Card;
import com.intuit.payment.data.Token;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.TokenService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to create token
 * 
 * @author dderose
 *
 */
public class TokenCreate {
	

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TokenCreate.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		createToken(requestContext);
		
	}

	
	private static void createToken(RequestContext requestContext) {
		
		TokenService tokenService = new TokenService(requestContext);
		
		Address address = new Address.Builder().region("CA").postalCode("94086")
				.streetAddress("1130 Kifer Rd").city("Sunnyvale").country("US")
				.build();
		
		Card card = new Card.Builder().expYear("2020").expMonth("02")
				.address(address).name("emulate=0").cvc("123").number("4111111111111111")
				.build();
				
		Token tokenRequest = new Token.Builder().card(card).build();
		
		Token token;
		
		try {
			token = tokenService.createToken(tokenRequest);
			LOG.info("getIntuit_tid:::" + token.getIntuit_tid());
			LOG.info("token:::" + token.getValue());
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	

}
