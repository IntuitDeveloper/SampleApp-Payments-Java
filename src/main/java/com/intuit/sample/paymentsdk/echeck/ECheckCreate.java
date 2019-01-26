package com.intuit.sample.paymentsdk.echeck;

import java.math.BigDecimal;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.BankAccount;
import com.intuit.payment.data.BankAccount.AccountType;
import com.intuit.payment.data.CheckContext;
import com.intuit.payment.data.DeviceInfo;
import com.intuit.payment.data.ECheck;
import com.intuit.payment.data.ECheck.PaymentModeType;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.ECheckService;
import com.intuit.sample.payment.helper.ResourceConfig;

/**
 * Demonstrates method to create echeck
 * 
 * @author dderose
 *
 */
public class ECheckCreate {
	
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ECheckCreate.class);
	private static Properties config  = ResourceConfig.getInstance();
	
	public static void main (String[] args) {
		
		String accessToken = config.getProperty("accessToken");
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
		createECheck(requestContext); 
		
	}

	private static void createECheck(RequestContext requestContext) {
		
		ECheckService eCheckService = new ECheckService(requestContext);
		
		
		BankAccount bankAccount = new BankAccount.Builder()
				.name("Fname LName").routingNumber("322079353")
				.accountNumber("11000000333456781").accountType(AccountType.PERSONAL_CHECKING)
				.phone("1234567890").build();
		
		DeviceInfo deviceInfo = new DeviceInfo.Builder()
				.id("1").type("type").longitude("longitude")
				.phoneNumber("phoneNumber").macAddress("macAddress").ipAddress("34")
				.build();
		CheckContext context = new CheckContext.Builder(deviceInfo).build();

		ECheck 	eCheckRequest = new ECheck.Builder()
				.amount(new BigDecimal("2.22")).bankAccount(bankAccount)
				.context(context).paymentMode(PaymentModeType.WEB)
				.checkNumber("12345678").description("Check Auth test call")
				.build();
		
		ECheck eCheck;
		
		try {
			eCheck = eCheckService.create(eCheckRequest);
			LOG.info("getIntuit_tid:::" + eCheck.getIntuit_tid());
			LOG.info("echeck id:::" + eCheck.getId());
		
		} catch (BaseException e) {
			LOG.error("Exception while calling API " + e.getMessage(), e);
		}
		
	
	}
	
}
