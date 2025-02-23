package org.example.springbootjava17;

import org.example.springbootjava17.config.IntegrationTest;
import org.example.springbootjava17.controller.UserController;
import org.example.springbootjava17.data.TestData;
import org.example.springbootjava17.dto.UserRecord;
import org.example.springbootjava17.entity.UserEntity;
import org.example.springbootjava17.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@IntegrationTest
public class UserControllerSpringBootTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TestData testData;

    @Test
    public void testCreateUser() {
        UserRecord userRecord = testData.getTestUserRecord();
        HttpEntity<UserRecord> request = new HttpEntity<>(userRecord);

        ResponseEntity<UserRecord> response = restTemplate.exchange("/user", HttpMethod.POST, request, UserRecord.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserRecord createdUser = response.getBody();
        assertNotNull(createdUser);
        assertEquals(userRecord.username(), createdUser.username());

        UserEntity savedEntity = userRepo.findByUsername(userRecord.username()).get();
        assertEquals(userRecord.username(), savedEntity.getUsername());
    }
}