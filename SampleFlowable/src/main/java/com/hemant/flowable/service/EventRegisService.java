package com.hemant.flowable.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hemant.flowable.entity.RegisEvent;
import com.hemant.flowable.entity.UserEventRegistration;
import com.hemant.flowable.payload.EventRequest;
import com.hemant.flowable.payload.TaskDetails;
import com.hemant.flowable.payload.UserPaymentRequest;
import com.hemant.flowable.payload.UserRegistrationRequest;
import com.hemant.flowable.repository.EventRepository;
import com.hemant.flowable.repository.UserEventRegistrationRepository;


@Service
public class EventRegisService {
	
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private UserEventRegistrationRepository userEventRegistrationRepository;
	
	public static final String TASK_ADMIN_GROUP = "EventAdmin";
	public static final String TASK_ORGANISER_GROUP = "organizerGroup";
    public static final String PROCESS_DEFINITION_KEY = "eventRegistrationProcess";
    public static final String ORGANISER = "organiser";
    public static final String PARTICIPANT = "participant";
	
	public ProcessInstance startProcess(String assignee) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("organiser", assignee);
		variables.put("participant", "participant");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("eventRegistrationProcess", variables);
		return processInstance;
	}
	
	public List<TaskDetails> getManagerTasks() {
        List<Task> tasks =
                taskService.createTaskQuery().taskCandidateGroup(TASK_ADMIN_GROUP).list();
        List<TaskDetails> taskDetails = getTaskDetails(tasks);

        return taskDetails;
    }
	
	public List<TaskDetails> getuserTasks(String username) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();
        List<TaskDetails> taskDetails = getTaskDetails(tasks);

        return taskDetails;
    }

    private List<TaskDetails> getTaskDetails(List<Task> tasks) {
        List<TaskDetails> taskDetails = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> processVariables = taskService.getVariables(task.getId());
            taskDetails.add(new TaskDetails(task.getId(), task.getName(), processVariables));
        }
        return taskDetails;
    }


    public void approveOrganizer(String processid,Boolean approved) {
    	Task assignedtask = taskService.createTaskQuery().processInstanceId(processid).taskCandidateGroup(TASK_ADMIN_GROUP).list().stream().filter(task -> task.getName().equals("Approve Organisazer")).findFirst().get();
    	 Map<String, Object> processVariables = taskService.getVariables(assignedtask.getId());
    	 Group committeeGroup = identityService.createGroupQuery().groupId("committeeGroup").singleResult();
 		 User organoserUser = identityService.createUserQuery().memberOfGroup(committeeGroup.getId()).list().stream().filter(usr-> usr.getId().equals(processVariables.get("organiser"))).findFirst().get();
 		 Group organiserGroup = identityService.createGroupQuery().groupId("organizerGroup").singleResult();
 		 
 		if(!identityService.createUserQuery().memberOfGroup(organiserGroup.getId()).list().stream().anyMatch(usr-> usr.getId().equals(processVariables.get("organiser")))) {
 			 identityService.createMembership(organoserUser.getId(), organiserGroup.getId());
 		}
 		  		
 		 Map<String, Object> variables = new HashMap<String, Object>();
 		 variables.put("organiserApproved", approved.booleanValue());
 		 taskService.complete(assignedtask.getId(), variables);
    }

    public RegisEvent registerEvent(EventRequest eventRequest,String processid) {
    	String organiser = (String) runtimeService.getVariable(processid, ORGANISER);
    	Task assignedtask = 	taskService.createTaskQuery().processInstanceId(processid).taskCandidateOrAssigned(organiser).list().stream().filter(task -> task.getName().equals("Create Event")).findFirst().get();
    	RegisEvent regisEvent = eventRepository.save(RegisEvent.builder().eventname(eventRequest.getEventname()).topic(eventRequest.getTopic()).eventdate(eventRequest.getEventdate()).location(eventRequest.getLocation()).paidevent(eventRequest.getPaidevent()).build());
    	 Map<String, Object> variables = new HashMap<String, Object>();
    	variables.put("EventCreated", eventRequest.getEventname());
 		 taskService.complete(assignedtask.getId(), variables);
 		 return regisEvent;
    }
    
    public UserEventRegistration registerUser(UserRegistrationRequest userRegistrationRequest,String processid) {
    	String barcode = null;
    	String participant = (String) runtimeService.getVariable(processid, PARTICIPANT);
    	Task assignedtask = 	taskService.createTaskQuery().processInstanceId(processid).taskCandidateOrAssigned(participant).list().stream().filter(task -> task.getName().equals("Register Participant")).findFirst().get();
    	Group participantGroup = identityService.createGroupQuery().groupId("EventParticipant").singleResult();
    	User participantUser = identityService.createUserQuery().userIdIgnoreCase(userRegistrationRequest.getUsername()).singleResult();
    	if(participantUser == null) {
    		participantUser = identityService.newUser(userRegistrationRequest.getUsername());
    		participantUser.setFirstName(userRegistrationRequest.getFirstname());
    		participantUser.setLastName(userRegistrationRequest.getLastname());
  		  	identityService.saveUser(participantUser);
  		  	identityService.createMembership(participantUser.getId(), participantGroup.getId());
    	}else {
    		if(!identityService.createUserQuery().memberOfGroup(participantGroup.getId()).list().stream().anyMatch(usr-> usr.getId().equals(userRegistrationRequest.getUsername()))) {
   			 identityService.createMembership(participantUser.getId(), participantGroup.getId());
    		}
    	}
    	RegisEvent regisEvent = eventRepository.findByEventname(userRegistrationRequest.getEventname()).get(0);
    	 if(!regisEvent.getPaidevent()) {
    		 barcode = userRegistrationRequest.getUsername()+"barcode";
    	 }
    	UserEventRegistration userEventRegistration =  userEventRegistrationRepository.save(UserEventRegistration.builder().eventid(regisEvent.getId()).userid(userRegistrationRequest.getUsername()).registrationdate(new Date()).userbarcode(barcode).build());
    	runtimeService.setVariable(processid, "participant", userRegistrationRequest.getUsername());
    	 Map<String, Object> variables = new HashMap<String, Object>();
    	 if(!regisEvent.getPaidevent()) {
    		variables.put("username", userEventRegistration.getUserid());
         	variables.put("eventname", regisEvent.getEventname());
         	variables.put("email", "test@example.com");
         	variables.put("barcode", barcode);
    	 }
    	variables.put("paymentRequired", regisEvent.getPaidevent());
    	taskService.complete(assignedtask.getId(), variables);
    	return userEventRegistration;
    }
    
    public UserEventRegistration makepayment(UserPaymentRequest userPaymentRequest,String processid) {
    	String participant = (String) runtimeService.getVariable(processid, PARTICIPANT);
    	Task assignedtask = taskService.createTaskQuery().processInstanceId(processid).taskCandidateOrAssigned(participant).list().stream().filter(task -> task.getName().equals("Payment Participant")).findFirst().get();
    	RegisEvent regisEvent = eventRepository.findByEventname(userPaymentRequest.getEventname()).get(0);
    	UserEventRegistration userEventRegistration = userEventRegistrationRepository.findByEventidAndUserid(regisEvent.getId(),userPaymentRequest.getUserid()).get(0);
    	userEventRegistration.setPaymentamount(userPaymentRequest.getPayment());
    	userEventRegistration.setPaymentdate(new Date());
    	userEventRegistrationRepository.save(userEventRegistration);
    	Map<String, Object> variables = new HashMap<String, Object>();
    	variables.put("eventRegistrationId", userEventRegistration.getId());
    	taskService.complete(assignedtask.getId(), variables);
    	return userEventRegistration;
    }
    
    public UserEventRegistration validatePayment(String processid) {
    	String organiser = (String) runtimeService.getVariable(processid, ORGANISER);
    	Task assignedtask = taskService.createTaskQuery().processInstanceId(processid).taskCandidateOrAssigned(organiser).list().stream().filter(task -> task.getName().equals("Validate Payment")).findFirst().get();
    	Map<String, Object> processVariables = taskService.getVariables(assignedtask.getId());   	
    	UserEventRegistration userEventRegistration = userEventRegistrationRepository.findById((Long)processVariables.get("eventRegistrationId")).get();
    	Map<String, Object> variables = new HashMap<String, Object>();
    	variables.put("eventRegistrationId", (Long)processVariables.get("eventRegistrationId"));
    	if(userEventRegistration.getPaymentamount() > 0) {
    		variables.put("paymentValidated", Boolean.TRUE);
    	}else {
    		variables.put("paymentValidated", Boolean.FALSE);
    	}
 		 taskService.complete(assignedtask.getId(), variables);
 		 return userEventRegistration;
    }
    
    public UserEventRegistration verifyPayment(String processid) {
    	String organiser = (String) runtimeService.getVariable(processid, ORGANISER);
    	Task assignedtask = taskService.createTaskQuery().processInstanceId(processid).taskCandidateOrAssigned(organiser).list().stream().filter(task -> task.getName().equals("validate payment")).findFirst().get();
    	Map<String, Object> processVariables = taskService.getVariables(assignedtask.getId());   	
    	UserEventRegistration userEventRegistration = userEventRegistrationRepository.findById((Long)processVariables.get("eventRegistrationId")).get();
    	RegisEvent regisEvent = eventRepository.findById(userEventRegistration.getEventid()).get();
    	Map<String, Object> variables = new HashMap<String, Object>();
    	variables.put("eventRegistrationId", (Long)processVariables.get("eventRegistrationId"));
    	if(userEventRegistration.getPaymentamount() > 0) {
    		userEventRegistration.setPaymentstatus("SUCCESS");   		
    		String barcode = userEventRegistration.getUserid()+"barcode";
    		userEventRegistration.setUserbarcode(barcode);
    		userEventRegistrationRepository.save(userEventRegistration);
    		variables.put("paymentValidated", Boolean.TRUE);
    		variables.put("username", userEventRegistration.getUserid());
        	variables.put("eventname", regisEvent.getEventname());
        	variables.put("email", "test@example.com");
        	variables.put("barcode", barcode);
    	}else {
    		variables.put("paymentValidated", Boolean.FALSE);
    	} 
 		 taskService.complete(assignedtask.getId(), variables);
 		 return userEventRegistration;
    }
    
    public void acceptQRCode(String processid) {
    	String participant = (String) runtimeService.getVariable(processid, PARTICIPANT);
    	Task assignedtask = 	taskService.createTaskQuery().processInstanceId(processid).taskCandidateOrAssigned(participant).list().stream().filter(task -> task.getName().equals("accept QRCode")).findFirst().get();
        taskService.complete(assignedtask.getId());
    }



}
