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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void getSuitableLoanAmountNotFoundResponse() {
        EngineRequest request = new EngineRequest("10000000000", 4000, 12);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getSuitableLoanAmountNoContentResponse() {
        EngineRequest request = new EngineRequest("49002010965", 4000, 12);
        ResponseEntity<EngineResponse> response = this.restTemplate.postForEntity(url + port + "/api/engine", request, EngineResponse.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }
}
