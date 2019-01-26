package com.intuit.sample.paymentsdk.bankaccount;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.BankAccount;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.BankAccountService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to get bankaccount by id
 * 
 * @author dderose
 *
 */
public class BankAccountGetById {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BankAccountGetById.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		getBankAccount(requestContext);
	}

	private static void getBankAccount(RequestContext requestContext) {
		
		BankAccountService bankAccountService = new BankAccountService(requestContext);	
		BankAccount bankAccount;
		
		try {
			bankAccount = bankAccountService.getBankAccount("add customer id", "add bank account id");
			LOG.info("getIntuit_tid:::" + bankAccount.getIntuit_tid());
			LOG.info("bankAccount id:::" + bankAccount.getId() );
			
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
