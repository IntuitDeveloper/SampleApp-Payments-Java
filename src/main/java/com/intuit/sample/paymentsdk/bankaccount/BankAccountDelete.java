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
 * Demonstrates method to delete bankaccount
 * 
 * @author dderose
 *
 */
public class BankAccountDelete {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BankAccountDelete.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		deleteBankAccount(requestContext);
		
	}

	private static void deleteBankAccount(RequestContext requestContext) {
		
		BankAccountService bankAccountService = new BankAccountService(requestContext);	
		try {
			BankAccount bankAccount = bankAccountService.delete("add customer id", "add bank account id");
			LOG.info("getIntuit_tid:::" + bankAccount.getIntuit_tid());
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
