package com.intuit.sample.payment;

import java.io.IOException;
import java.util.Properties;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.sample.payment.helper.MapperHelper;
import com.intuit.sample.payment.helper.PaymentHelper;
import com.intuit.sample.payment.helper.ResourceConfig;
import com.intuit.sample.payment.model.Card;
import com.intuit.sample.payment.model.Token;
import com.intuit.sample.payment.service.PaymentService;

/**
 * Demonstrates method to create a token
 * 
 * @author dderose
 *
 */
public class TokenCreate {
	
	private static ObjectMapper mapper  = MapperHelper.getInstance();
	private static Properties config  = ResourceConfig.getInstance();
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TokenCreate.class);
	
	public static void main(String[] args) throws IOException {
		
		String url = config.getProperty("tokenUrl");
		
		//create token request
		Card card = PaymentHelper.getCardFields();
		Token token = PaymentHelper.generateTokenRequest(card);
        String json = mapper.writeValueAsString(token);
        
        //call API
        String result = PaymentService.callAPI(url, json, null);
        
        //retrieve token value
        JSONObject jsonObj = new JSONObject(result);
        String paymentToken = jsonObj.getString("value");
        LOG.info("token value:" + paymentToken);
	}

}
