package com.hemant.flowable.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistrationResponse {

	String processId;
	
	boolean ended;
	
	Object responseData;
	
}
