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
import com.intuit.sample.payment.service.PaymentService;

/**
 * Demonstrates method to create charge
 * 
 * @author dderose
 *
 */
public class ChargeCreate {
	
	private static ObjectMapper mapper  = MapperHelper.getInstance();
	private static Properties config  = ResourceConfig.getInstance();
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ChargeCreate.class);
	
	public static void main(String[] args) throws IOException {
		
		String url = config.getProperty("chargeUrl");
		String accessToken = config.getProperty("accessToken");
		
		//create a charge
        Charge charge = PaymentHelper.createCharge();
        String json = mapper.writeValueAsString(charge);
        
        //call API
        String result = PaymentService.callAPI(url, json, accessToken);
        JSONObject jsonObj = new JSONObject(result);
        String id = jsonObj.getString("id");
        LOG.info("charge id:" + id);
         
	}

}
