Event Registration workflow using Spring Boot and Flowable.

Project Setup Steps-

  (1)CheckOut project
  
  (2)Create database schema
  
  (3)Run Project
  
  
Project Feature -

  A Sample Spring Boot Flowable workflow project, where process managed by 3 group-
  
    (1) Admin - Provide authority to Event Organiser to create and Manage Event
    (2)Organiser - Create event , manage event and manage participant.
    (3)Participant - Participate in Event
    
   Project Flow-
   
   (1) Committee Group member Start process ,pick a member and send for approval to Admin.
   (2) After Approval from Admin,Event Organiser create event 
   (3) Participant Register after Event createion and pay for Event if applicable
   (4) Organiser verify payment and create & send barcode to participant 
   (5) Participant accept barcode for event.
   
   Technology-
   
   Flowable used for workflow management.
   JPA and MySql used for data persistence.
   Spring Security used for restricted access to different group operation. For Authentication Http Basic Authentication used while for 
   Authorization method level  authorization used based on operation authority. Flowable IDM used for Authentication and Authorization.
   
   
   
    
