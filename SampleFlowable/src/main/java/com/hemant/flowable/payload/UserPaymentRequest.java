package com.hemant.flowable.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentRequest {
	
	String userid;
	
	String eventname;
	
	Integer payment;
	

}
