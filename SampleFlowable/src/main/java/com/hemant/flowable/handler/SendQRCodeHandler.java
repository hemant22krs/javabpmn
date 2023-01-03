package com.hemant.flowable.handler;


import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hemant.flowable.entity.RegisEvent;
import com.hemant.flowable.entity.UserEventRegistration;
import com.hemant.flowable.repository.EventRepository;
import com.hemant.flowable.repository.UserEventRegistrationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service
@Scope("prototype")
public class SendQRCodeHandler  implements JavaDelegate {
	
	private Expression username;
	private Expression eventname;
	private Expression email;
	private Expression barcode;
	
	
	@Override
	public void execute(DelegateExecution execution) {
		String usernameValue = (String) username.getValue(execution);
		String eventnameValue = (String) eventname.getValue(execution);
		String emailValue = (String) email.getValue(execution);
		String barcodeValue = (String) barcode.getValue(execution);
		 log.info("value of usernameValue,eventnameValue,emailValue in SendQRCodeHandler---"+usernameValue+"---"+eventnameValue+"---"+emailValue);
		 log.info("barcode sent in SendQRCodeHandler---"+barcodeValue);
    	//sent barcode to user
	}

}
