package com.hemant.flowable.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessInstanceResponse {
	
	String processId;
	
	boolean ended;
	
    Object responseData;
	
	
}
