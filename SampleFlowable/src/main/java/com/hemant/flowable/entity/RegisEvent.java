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
@Table(name="REGISEVENT")
public class RegisEvent {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    @Column(name="eventname")
	private String eventname;

    @Column(name="topic")
	private String topic;

    @Column(name="eventdate")
	private Date eventdate;
    
    @Column(name="location")
	private String location;
    
    @Column(columnDefinition = "BIT")
    private Boolean paidevent;
    
    
}
