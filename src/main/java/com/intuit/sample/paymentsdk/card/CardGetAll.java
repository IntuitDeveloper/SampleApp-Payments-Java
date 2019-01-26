package com.intuit.sample.paymentsdk.card;

import java.util.List;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Card;
import com.intuit.payment.data.QueryResponse;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.CardService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to get all cards for a customer
 * 
 * @author dderose
 *
 */
public class CardGetAll {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CardGetAll.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		getAllCards(requestContext);
		
	}

	private static void getAllCards(RequestContext requestContext) {
		
		CardService cardService = new CardService(requestContext);	
		
		try {
			QueryResponse response = cardService.getAllCards("add customer id");
			//Use the method below to retrieve more than 10 cards
			//QueryResponse response = cardService.getAllCards("add customer id", 20);
			List<Card> cards = response.getCards();
			LOG.info("card list size:::" + cards.size());
			LOG.info("getIntuit_tid:::" + response.getIntuit_tid());
			
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
		
	}
	
	
}
