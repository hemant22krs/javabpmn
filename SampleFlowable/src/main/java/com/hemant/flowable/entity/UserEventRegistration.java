package com.hemant.flowable.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Entity
@Table(name="USER_EVENT_REGIS")
public class UserEventRegistration {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    @Column(name="eventid")
	private Long eventid;

    @Column(name="userid")
	private String userid;

    @Column(name="registrationdate")
	private Date registrationdate;
    
    @Column(name="paymentamount")
    private Integer paymentamount;
    
    @Column(name="paymentdate")
	private Date paymentdate;
    
    @Column(name = "paymentstatus")
    private String paymentstatus;
    
    @Column(name = "userbarcode")
    private String userbarcode;

	
}
