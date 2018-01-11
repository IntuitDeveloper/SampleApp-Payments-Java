package com.intuit.sample.payment;

import java.io.IOException;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.intuit.sample.payment.helper.MapperHelper;
import com.intuit.sample.payment.helper.PaymentHelper;
import com.intuit.sample.payment.helper.ResourceConfig;
import com.intuit.sample.payment.model.Charge;
import com.intuit.sample.payment.model.Token;
import com.intuit.sample.payment.service.PaymentService;

/**
 * Demonstrates method to create a charge using token
 * 
 * @author dderose
 *
 */
public class ChargeUsingTokenCreate {
	
	private static ObjectMapper mapper  = MapperHelper.getInstance();
	private static Properties config  = ResourceConfig.getInstance();
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ChargeUsingTokenCreate.class);
	
	public static void main(String[] args) throws IOException {
		
		String url = config.getProperty("chargeUrl");
		String tokenurl = config.getProperty("tokenUrl");
		String accessToken = config.getProperty("accessToken");
		
		//create token 
		Token token = PaymentHelper.generateTokenRequest(PaymentHelper.getCardFields());
        String json = mapper.writeValueAsString(token);
        
        String result = PaymentService.callAPI(tokenurl, json, null);
        JSONObject jsonObj = new JSONObject(result);
        String paymentToken = jsonObj.getString("value");
        
        //create charge using token 
        Charge charge = PaymentHelper.createChargeUsingToken(paymentToken);
        json = mapper.writeValueAsString(charge);
        
        //call API
        result = PaymentService.callAPI(url, json, accessToken);
        jsonObj = new JSONObject(result);
        String id = jsonObj.getString("id");
        LOG.info("charge id:" + id);
         
	}

}
