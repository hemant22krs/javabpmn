package com.hemant.flowable.controller;

import java.util.List;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.hemant.flowable.payload.EventOrganizer;
import com.hemant.flowable.payload.EventRequest;
import com.hemant.flowable.payload.ProcessInstanceResponse;
import com.hemant.flowable.payload.TaskDetails;
import com.hemant.flowable.payload.UserPaymentRequest;
import com.hemant.flowable.payload.UserRegistrationRequest;
import com.hemant.flowable.service.EventRegisService;

@RestController
public class EventOrganizerController {
	
	@Autowired
	EventRegisService eventManagementService;
	
	@PostMapping(value = "/organiser/process")
	@PreAuthorize("hasAuthority('committeeGroup')")
	public ProcessInstanceResponse startProcessInstance(@RequestBody EventOrganizer eventOrganizer) {
		ProcessInstance processInstance =  eventManagementService.startProcess(eventOrganizer.getUsername());
		//  return new ProcessInstanceResponse(processInstance.getId(), processInstance.isEnded(),null);
		return ProcessInstanceResponse.builder().processId(processInstance.getId()).ended(processInstance.isEnded()).build();
	}

	@GetMapping("/manager/tasks")
	@PreAuthorize("hasAuthority('EventAdmin')")
    public List<TaskDetails> getTasks() {
        return eventManagementService.getManagerTasks();
    }

	@GetMapping("/user/{username}/tasks")
    public List<TaskDetails> getOrganiserTasks(@PathVariable("username") String username) {
        return eventManagementService.getuserTasks(username);
    }


    @PostMapping("/manager/approve/tasks/{processid}/{approved}")
    @PreAuthorize("hasAuthority('EventAdmin')")
    public ProcessInstanceResponse approveTask(@PathVariable("processid") String processid,@PathVariable("approved") Boolean approved){
    	eventManagementService.approveOrganizer(processid,approved);
    	return ProcessInstanceResponse.builder().processId(processid).ended(false).build();
    }
    
    @PostMapping(value = "/organiser/{processid}/registerEvent")
	@PreAuthorize("hasAuthority('organizerGroup')")
	public ProcessInstanceResponse eventRegistration(@PathVariable("processid") String processid,@RequestBody EventRequest eventRequest) {
		return ProcessInstanceResponse.builder().processId(processid).ended(false).responseData(eventManagementService.registerEvent(eventRequest,processid)).build();
	}

    @PostMapping("/user/registration/{processid}")
    public ProcessInstanceResponse userRegistration(@PathVariable("processid") String processid,@RequestBody UserRegistrationRequest userRegistrationRequest){
    	return ProcessInstanceResponse.builder().processId(processid).ended(false).responseData(eventManagementService.registerUser(userRegistrationRequest,processid)).build();
    }
    
    @PatchMapping("/user/makepayment/{processid}")
    public ProcessInstanceResponse makemayment(@PathVariable("processid") String processid,@RequestBody UserPaymentRequest userPaymentRequest){
    	return ProcessInstanceResponse.builder().processId(processid).ended(false).responseData(eventManagementService.makepayment(userPaymentRequest,processid)).build();
    }
    
    @PostMapping("/organiser/{processid}/validatePayment")
    @PreAuthorize("hasAuthority('organizerGroup')")
    public ProcessInstanceResponse validatePayment(@PathVariable("processid") String processid){
    	return ProcessInstanceResponse.builder().processId(processid).ended(false).responseData(eventManagementService.validatePayment(processid)).build();
    }
    
    @PatchMapping("/organiser/{processid}/verifyPayment")
    @PreAuthorize("hasAuthority('organizerGroup')")
    public ProcessInstanceResponse verifyPayment(@PathVariable("processid") String processid){
    	return ProcessInstanceResponse.builder().processId(processid).ended(false).responseData(eventManagementService.verifyPayment(processid)).build();
    }
    
    @PostMapping("/user/accept/{processid}")
    public void acceptHoliday(@PathVariable("processid") String processid){
    	eventManagementService.acceptQRCode(processid);
    }

  
}
