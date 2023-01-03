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
public class UserRegistrationRequest {
	
	String username;
	
	String firstname;
	
	String lastname;
	
	Date dateofbirth;
	
	String address;
	
	String city;
	
	String state;
	
	String zipcode;
	
	String eventname;

	
}
