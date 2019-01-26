package com.intuit.sample.paymentsdk.card;

import java.util.Properties;
import java.util.UUID;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Address;
import com.intuit.payment.data.Card;
import com.intuit.payment.data.Token;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.CardService;
import com.intuit.payment.services.TokenService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to create card for a customer
 * 
 * @author dderose
 *
 */
public class CardCreate {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CardCreate.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		createCard(requestContext);
		
		//set new request id
		requestContext.setRequestId(UUID.randomUUID().toString().replace("-", ""));
		createCardFromToken(requestContext);
	}

	private static void createCard(RequestContext requestContext) {
		
		CardService cardService = new CardService(requestContext);
		
		Address address = new Address.Builder().region("VA")
				.postalCode("44112").streetAddress("1245 Hana Rd")
				.city("Richmond").country("US").build();
		
		Card cardRequest = new Card.Builder().expYear("2026").expMonth("12")
				.address(address).name("Test User").number("4408041234567893").build();
		
		Card card;
		
		try {
			card = cardService.create(cardRequest, "add customer id");
			LOG.info("getIntuit_tid:::" + card.getIntuit_tid());
			LOG.info("card:::" + card.getId());
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
	private static void createCardFromToken(RequestContext requestContext) {
		
		TokenService tokenService = new TokenService(requestContext);
		
		Address address = new Address.Builder().region("CA")
				.postalCode("94086").streetAddress("1130 Kifer Rd")
				.city("Sunnyvale").country("US").build();
		
		Card card = new Card.Builder()
				.expYear("2020").expMonth("02")
				.address(address).name("emulate=0")
				.cvc("123").number("4111111111111111").build();
				
		Token tokenRequest = new Token.Builder().card(card).build();
		Card cardResponse;
		
		try {
			Token token = tokenService.createToken(tokenRequest);
			LOG.info("getIntuit_tid:::" + token.getIntuit_tid());
			LOG.info("value:::" + token.getValue());
			
			//
			requestContext.setRequestId(UUID.randomUUID().toString().replace("-", ""));
			CardService cardService = new CardService(requestContext);
			cardResponse = cardService.createFromToken(token, "add customer id");
			LOG.info("getIntuit_tid:::" + cardResponse.getIntuit_tid());
			LOG.info("card:::" + cardResponse.getId());
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}		
	}
	
}
