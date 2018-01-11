package com.intuit.sample.payment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.exception.OAuthException;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Class containing service methods to call PaymentAPI
 * 
 * @author dderose
 *
 */
public final class PaymentService {
	
	private PaymentService() {}
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PaymentService.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	private static final CloseableHttpClient CLIENT = HttpClientBuilder.create().build();

	/**
	 * @param url - API url
	 * @param json - Json to be passed to the POST body
	 * @param accessToken - OAuth accessToken
	 * @return
	 */
	public static String callAPI(String url, String json, String accessToken) {
		
		HttpPost post = new HttpPost(url);
		
		//add header
		post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        if(accessToken != null && !accessToken.isEmpty()) {
        	post.setHeader("Authorization", "Bearer " + accessToken);
        	post.setHeader("Request-Id", UUID.randomUUID().toString().replace("-", ""));
        }
        
        LOG.info(json);
        HttpEntity entity = new StringEntity(json, "UTF-8");
	    post.setEntity(entity);
	    
	    try {
			HttpResponse response = CLIENT.execute(post);
			
			LOG.info("Response status:::" + response.getStatusLine().toString());
			
			if (response.getStatusLine().getStatusCode() == 401){
				
				//refresh token
				OAuth2Config oauth2Config = new OAuth2Config.OAuth2ConfigBuilder(config.getProperty("clientId"), config.getProperty("clientSecret")) 
											.callDiscoveryAPI(Environment.SANDBOX)
											.buildConfig();
				OAuth2PlatformClient client = new OAuth2PlatformClient(oauth2Config);
				BearerTokenResponse bearerTokenResponse;
				try {
					bearerTokenResponse = client.refreshToken(config.getProperty("refreshToken"));
					// TODO Save the tokens at this point - for demonstration, this is not done
					
		        	post.setHeader("Authorization", "Bearer " + bearerTokenResponse.getAccessToken());
		        	post.setHeader("Request-Id", UUID.randomUUID().toString().replace("-", ""));
		        	
					//call API again
		        	response = CLIENT.execute(post);
					
				} catch (OAuthException e) {
					LOG.error("Error calling API", e.getCause());
					return new JSONObject().put("response","error calling API").toString();
				}
			}
			
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201){
				String result = getResult(response).toString();
				LOG.info(result);
				return result;
				
            } else {
            	LOG.info("failed calling API");
                return new JSONObject().put("response","error calling API").toString();
            }
            
			
		    
		} catch (Exception e) {
			LOG.error("Error calling API", e.getCause());
			return new JSONObject().put("response","error calling API").toString();
		}
		
	}
	
	/**
	 * @param url
	 * @param accessToken
	 * @return
	 */
	public static String callAPIGET(String url, String accessToken) {
		
		HttpGet httprequest = new HttpGet(url);
		
		//add header
		httprequest.setHeader("Content-Type", "application/json");
		httprequest.setHeader("Accept", "application/json");
        if(accessToken != null && !accessToken.isEmpty()) {
        	httprequest.setHeader("Authorization", "Bearer " + accessToken);
        	httprequest.setHeader("Request-Id", UUID.randomUUID().toString().replace("-", ""));
        }
 	    
	    try {
			HttpResponse response = CLIENT.execute(httprequest);
			
			if (response.getStatusLine().getStatusCode() == 401){
				
				//refresh token
				OAuth2Config oauth2Config = new OAuth2Config.OAuth2ConfigBuilder(config.getProperty("clientId"), config.getProperty("clientSecret")) 
											.callDiscoveryAPI(Environment.SANDBOX)
											.buildConfig();
				OAuth2PlatformClient client = new OAuth2PlatformClient(oauth2Config);
				BearerTokenResponse bearerTokenResponse;
				try {
					bearerTokenResponse = client.refreshToken(config.getProperty("refreshToken"));
					// TODO Save the tokens at this point - for demonstration, this is not done
					
					httprequest.setHeader("Authorization", "Bearer " + bearerTokenResponse.getAccessToken());
					httprequest.setHeader("Request-Id", UUID.randomUUID().toString().replace("-", ""));
		        	
					//call API again
		        	response = CLIENT.execute(httprequest);
					
				} catch (OAuthException e) {
					LOG.error("Error calling API", e.getCause());
					return new JSONObject().put("response","error calling API").toString();
				}
			}
			
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201){
				String result = getResult(response).toString();
				LOG.info(result);
				return result;
				
            } else {
            	LOG.info("failed calling API");
                return new JSONObject().put("response","error calling API").toString();
            }
		    
		} catch (Exception e) {
			LOG.error("Error calling API", e.getCause());
			return new JSONObject().put("response","error calling API").toString();
		}
		
	}
	
	public static StringBuffer getResult(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
		    result.append(line);
		}
		return result;
	}
}
