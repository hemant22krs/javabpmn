package com.hemant.flowable.payload;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetails {
	
	 String taskId;
	 
	 String taskName;
	 
	 Map<String, Object> taskData;
	  

}
