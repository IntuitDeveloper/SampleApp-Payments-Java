package com.intuit.sample.payment.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.intuit.sample.payment.model.Address;
import com.intuit.sample.payment.model.Card;
import com.intuit.sample.payment.model.Charge;
import com.intuit.sample.payment.model.PaymentContext;
import com.intuit.sample.payment.model.Token;

/**
 * Helper method to create request
 * 
 * @author dderose
 *
 */
public final class PaymentHelper {
	
	private PaymentHelper() {}
	
	static public final String CHARGE_DESCRIPTION = "Payment Sample App";
	
	public static Charge createCharge() throws IOException {
		Charge charge = new Charge();

        // Only Authorize funds in this stage - set to 'true' to authorize and capture in one step
        charge.setCapture(false);

        // We created the charge today
        charge.setCreated(new Date());

        // We also need to set currency
        charge.setCurrency("USD");

        // Set Description - what will the customer see on their statement?
        charge.setDescription(CHARGE_DESCRIPTION);

        // Set the charge amount
        charge.setAmount(new BigDecimal("10.55"));

        // Supply the credit card information 
        charge.setCard(PaymentHelper.getCardFields());
        
        //set mobile and ecommerce indicators
        PaymentContext context = new PaymentContext();
        context.setMobile("false");
        context.setIsEcommerce("true");
        
        charge.setContext(context);
		return charge;
	}
	
	public static Charge createChargeUsingToken(String paymentToken) {
		Charge charge = new Charge();

        // Only Authorize funds in this stage - set to 'true' to authorize and capture in one step
        charge.setCapture(false);

        // We created the charge today
        charge.setCreated(new Date());

        // We also need to set currency
        charge.setCurrency("USD");

        // Set Description - what will the customer see on their statement?
        charge.setDescription(CHARGE_DESCRIPTION);

        // Set the charge amount
        charge.setAmount(new BigDecimal("10.55"));

        // Supply the credit card information in the form of a payment token
        charge.setToken(paymentToken);
        
        //set mobile and ecommerce indicators
        PaymentContext context = new PaymentContext();
        context.setMobile("false");
        context.setIsEcommerce("true");
        
        charge.setContext(context);
		return charge;
	}

	public static Token generateTokenRequest(Card card) {
		Token token = new Token();
        token.setCard(card);
		return token;
	}
	
	public static Card getCardFields() throws IOException {
		Card card = new Card();
		card.setName("emulate=0");
		card.setCvc("123");
		card.setNumber("4111111111111111");
		card.setExpMonth("02");
		card.setExpYear("2020");
		Address address = new Address();
		address.setStreetAddress("1130 Kifer Rd");
		address.setCity("Sunnyvale");
		address.setRegion("CA");
		address.setPostalCode("94086");
		address.setCountry("US");
		card.setAddress(address);
		
		return card;
	}
}
