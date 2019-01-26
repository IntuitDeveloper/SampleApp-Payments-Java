package com.intuit.sample.paymentsdk.card;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Card;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.CardService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to get card by id
 * 
 * @author dderose
 *
 */
public class CardGetById {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CardGetById.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		getCard(requestContext);
	}

	private static void getCard(RequestContext requestContext) {
		
		CardService cardService = new CardService(requestContext);	
		Card card;
		
		try {
			card = cardService.getCard("add customer id", "add card id");
			LOG.info("getIntuit_tid:::" + card.getIntuit_tid());
			LOG.info("card id:::" + card.getId() );
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
