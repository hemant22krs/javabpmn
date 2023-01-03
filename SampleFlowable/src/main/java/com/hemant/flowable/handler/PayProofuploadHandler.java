package com.hemant.flowable.handler;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayProofuploadHandler implements JavaDelegate  {

	@Override
	public void execute(DelegateExecution execution) {
		Long eventRegistrationId = (Long) execution.getVariable("eventRegistrationId");
		 execution.setVariable("eventRegistrationId", eventRegistrationId);
		 log.info("value of eventRegistrationId in PayProofuploadHandler---"+eventRegistrationId);
		 log.info("Payment photo proof uploaded in PayProofuploadHandler---");
	
	}

}
