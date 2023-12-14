package dev.renvl.engine.decision.web;

import dev.renvl.engine.decision.dto.EngineRequest;
import dev.renvl.engine.decision.dto.EngineResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;
    private final String url = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getSuitableLoanAmount() {
        EngineRequest request = new EngineRequest("49002010976", 4000, 12);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getSuitableLoanAmountMinimumAmountMinimumPeriod() {
        EngineRequest request = new EngineRequest("49002010976", 2000, 12);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        if (response.getBody() != null) {
            assertEquals(response.getBody().getLoanAmount(), 2000);
            assertEquals(response.getBody().getLoanPeriod(), 20);
        }
    }

    @Test
    public void getSuitableLoanAmountMinimumAmountMaximumPeriod() {
        EngineRequest request = new EngineRequest("49002010976", 2000, 60);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        if (response.getBody() != null) {
            assertEquals(response.getBody().getLoanAmount(), 6000);
            assertEquals(response.getBody().getLoanPeriod(), 60);
        }
    }

    @Test
    public void getSuitableLoanAmountMaximumAmountMinimumPeriod() {
        EngineRequest request = new EngineRequest("49002010976", 10000, 12);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        if (response.getBody() != null) {
            assertEquals(response.getBody().getLoanAmount(), 2000);
            assertEquals(response.getBody().getLoanPeriod(), 20);
        }
    }


    @Test
    public void getSuitableLoanAmountMaximumAmountMaximumPeriod() {
        EngineRequest request = new EngineRequest("49002010976", 10000, 60);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        if (response.getBody() != null) {
            assertEquals(response.getBody().getLoanAmount(), 6000);
            assertEquals(response.getBody().getLoanPeriod(), 60);
        }
    }

    @Test
    public void getSuitableLoanAmountNotFoundResponse() {
        EngineRequest request = new EngineRequest("10000000000", 4000, 12);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getSuitableLoanAmountBadRequestResponse() {
        EngineRequest request = new EngineRequest("49002010976", 1999, 61);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getSuitableLoanAmountNoContentResponse() {
        EngineRequest request = new EngineRequest("49002010965", 4000, 12);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void checkBodyResponse() {
        EngineRequest request = new EngineRequest("49002010976", 4000, 12);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        if (response.getBody() != null) {
            assertEquals(response.getBody().getLoanAmount(), 2000);
            assertEquals(response.getBody().getLoanPeriod(), 20);
        }
    }

    @Test
    public void invalidPersonalCodeBadRequest() {
        EngineRequest request = new EngineRequest("invalid_personal_code", 4000, 12);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Personal code must contain just numbers"));
    }

    @Test
    public void invalidLoanAmountBadRequest() {
        EngineRequest request = new EngineRequest("49002010976", -100, 12);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Loan amount must be greater than 2000"));
    }

    @Test
    public void invalidLoanPeriodBadRequest() {
        EngineRequest request = new EngineRequest("49002010976", 4000, 0);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Loan period must be greater than 12"));
    }

    @Test
    public void invalidRequestBodyBadRequest() {
        // Missing loanAmount in the request
        EngineRequest request = new EngineRequest("49002010976", null, 12);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Loan amount must not be null"));
    }

    @Test
    public void noneExistingRequest() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(url + port + "/api/none-existing-endpoint", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
