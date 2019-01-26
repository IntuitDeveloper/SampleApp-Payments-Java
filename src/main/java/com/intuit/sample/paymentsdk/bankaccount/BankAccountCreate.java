package com.intuit.sample.paymentsdk.bankaccount;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.BankAccount;
import com.intuit.payment.data.BankAccount.AccountType;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.BankAccountService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to create bank account for a customer
 * 
 * @author dderose
 *
 */
public class BankAccountCreate {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BankAccountCreate.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();		
		createBankAccount(requestContext);
		
	}

	private static void createBankAccount(RequestContext requestContext) {
		
		BankAccountService bankAccountService = new BankAccountService(requestContext);	
		
		BankAccount bankAccountRequest = new BankAccount.Builder()
				.name("My Checking").routingNumber("091000019")
				.accountNumber("120895674534").accountType(AccountType.PERSONAL_CHECKING)
				.phone("6047296480").build();
		BankAccount bankAccount;
		
		try {
			bankAccount = bankAccountService.create(bankAccountRequest, "add customer id");
			LOG.info("bankAccount:::" + bankAccount.getId());
			LOG.info("bankAccount:::" + bankAccount.getIntuit_tid());
			
			
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
	}
	
}
