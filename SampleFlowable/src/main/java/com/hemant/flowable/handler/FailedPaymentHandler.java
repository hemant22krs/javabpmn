package com.hemant.flowable.handler;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FailedPaymentHandler implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) {
		 log.info("Sent Payment failed mail in FailedPaymentHandler---");
	}

}
