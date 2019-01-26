package com.intuit.sample.paymentsdk.bankaccount;

import java.util.List;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.BankAccount;
import com.intuit.payment.data.QueryResponse;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.BankAccountService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to get all bankaccounts for a customer
 * 
 * @author dderose
 *
 */
public class BankAccountGetAll {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BankAccountGetAll.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		getAllBankAccounts(requestContext);
		
	}

	private static void getAllBankAccounts(RequestContext requestContext) {
		
		BankAccountService bankAccountService = new BankAccountService(requestContext);	
		
		try {
			QueryResponse response = bankAccountService.getAllBankAccounts("add customer id");
			List<BankAccount> bankAccounts = response.getBankAccounts();
			LOG.info("bank account list size:::" + bankAccounts.size());
			LOG.info("getIntuit_tid:::" + response.getIntuit_tid());
			
			
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
		
	}
	
}
