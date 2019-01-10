
package com.cg.app.validationAspect;

import java.util.logging.Logger;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.cg.app.account.SavingsAccount;
@Component
@Aspect
public class Validation {

	private Logger logger = Logger.getLogger(Validation.class.getName());

	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.deposit(..))")
	public void depositLogger(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("In deposite method");
		Object[] params = pjp.getArgs();
		double amount = (double) params[1];
		SavingsAccount account = (SavingsAccount) params[0];
		Double currentBalance = account.getBankAccount().getAccountBalance();
		if (amount > 0) {
			logger.info("Deposite Successful");
			pjp.proceed();
		} else {
			logger.info("Invallid Amount");
		}
	}
	
	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.withdraw(..))")
	public void withdrawLogger(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("In withdraw method");
		Object[] params = pjp.getArgs();
		double amount = (double) params[1];
		SavingsAccount account = (SavingsAccount) params[0];
		Double currentBalance = account.getBankAccount().getAccountBalance();
		logger.info("Amout to withdraw is:"+amount);
		if(amount>account.getBankAccount().getAccountBalance())
		logger.info("Insufficient Balance ");
		else if(amount<0)
			logger.info("Invalid amount ");
		else
		{
			pjp.proceed();
			logger.info("Successful Withdrawal");
		}
	}

}
