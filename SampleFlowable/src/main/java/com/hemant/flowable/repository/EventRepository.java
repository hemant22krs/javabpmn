package com.hemant.flowable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hemant.flowable.entity.RegisEvent;

@Repository
public interface EventRepository extends JpaRepository<RegisEvent, Long>{
	
	public List<RegisEvent> findByEventname(String eventname);
}
