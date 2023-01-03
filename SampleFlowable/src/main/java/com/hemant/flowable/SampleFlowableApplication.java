package com.hemant.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleFlowableApplication {

    public static void main(String[] args) {

        SpringApplication.run(SampleFlowableApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(final IdentityService identityService) {

        return new CommandLineRunner() {
            public void run(String... strings) throws Exception {

                Group adminGroup = identityService.createGroupQuery().groupId("EventAdmin").singleResult();
                if(adminGroup == null ) {
                    adminGroup = identityService.newGroup("EventAdmin");
                    identityService.saveGroup(adminGroup);
                }
                Group committeeGroup = identityService.createGroupQuery().groupId("committeeGroup").singleResult();
                if(committeeGroup == null) {
                    committeeGroup = identityService.newGroup("committeeGroup");
                    identityService.saveGroup(committeeGroup);
                }
                Group organiserGroup = identityService.createGroupQuery().groupId("organizerGroup").singleResult();
                if(organiserGroup == null) {
                    organiserGroup = identityService.newGroup("organizerGroup");
                    identityService.saveGroup(organiserGroup);
                }
                Group participantGroup = identityService.createGroupQuery().groupId("EventParticipant").singleResult();
                if(participantGroup == null) {
                    participantGroup = identityService.newGroup("EventParticipant");
                    identityService.saveGroup(participantGroup);
                }
                User testAdminUser = identityService.createUserQuery().userId("testAdminUser").singleResult();
                if(testAdminUser == null) {
                    testAdminUser = identityService.newUser("testAdminUser");
                    testAdminUser.setEmail("testadmin@example.com");
                    testAdminUser.setPassword("test");
                    testAdminUser.setFirstName("admin");
                    testAdminUser.setLastName("User");
                    identityService.saveUser(testAdminUser);
                    identityService.createMembership(testAdminUser.getId(), adminGroup.getId());
                }
                User testcommitteeUser1 = identityService.createUserQuery().userId("testcommitteeUser1").singleResult();
                if(testcommitteeUser1 == null) {
                    testcommitteeUser1 = identityService.newUser("testcommitteeUser1");
                    testcommitteeUser1.setEmail("testcommitteeUser1@example.com");
                    testcommitteeUser1.setPassword("Committee");
                    testcommitteeUser1.setFirstName("Committee");
                    testcommitteeUser1.setLastName("User");
                    identityService.saveUser(testcommitteeUser1);
                    identityService.createMembership(testcommitteeUser1.getId(), committeeGroup.getId());
                }

            }
        };
    }

}
