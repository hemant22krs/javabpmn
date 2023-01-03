package com.hemant.flowable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hemant.flowable.entity.UserEventRegistration;

@Repository
public interface UserEventRegistrationRepository extends JpaRepository<UserEventRegistration, Long>{

	List<UserEventRegistration> findByEventidAndUserid(Long eventid,String userid);
}
