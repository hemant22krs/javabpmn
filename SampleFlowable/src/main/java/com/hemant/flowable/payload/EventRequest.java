package com.hemant.flowable.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
	
		private String eventname;
   
		private String topic;
	   
		private Date eventdate;
	    	   
		private String location;
		
		private Boolean paidevent; 
}
