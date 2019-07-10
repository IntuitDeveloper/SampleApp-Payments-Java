package com.intuit.sample.payment;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.sample.payment.helper.MapperHelper;
import com.intuit.sample.payment.helper.PaymentHelper;
import com.intuit.sample.payment.helper.ResourceConfig;
import com.intuit.sample.payment.model.Charge;
import com.intuit.sample.payment.service.PaymentService;

/**
 * Demonstrates method to create a charge and retrieve it
 * 
 * @author dderose
 *
 */
public class ChargeRetrieve {
	
	private static ObjectMapper mapper  = MapperHelper.getInstance();
	private static Properties config  = ResourceConfig.getInstance();
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ChargeRetrieve.class);
	
	
	public static void main(String[] args) throws IOException {
		
		String url = config.getProperty("chargeUrl");
		String accessToken = config.getProperty("accessToken");
		//create a charge
        Charge charge = PaymentHelper.createCharge();
        String json = mapper.writeValueAsString(charge);
        
        String result = PaymentService.callAPI(url, json, accessToken);
        Charge chargeResponse = mapper.readValue(result, Charge.class);
        LOG.info("charge id:" + chargeResponse.getId());
        
        //retrive charge
        PaymentService.callAPIGET(url+"/"+chargeResponse.getId(), accessToken);
         
	}

}
