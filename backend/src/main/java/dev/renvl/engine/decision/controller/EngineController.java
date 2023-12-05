package dev.renvl.engine.decision.controller;

import dev.renvl.engine.decision.dto.EngineRequest;
import dev.renvl.engine.decision.dto.EngineResponse;
import dev.renvl.engine.decision.service.EngineService;
import dev.renvl.engine.decision.utils.ResourceBadRequestException;
import dev.renvl.engine.decision.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin
class EngineController {

    private final EngineService service;

    @Autowired
    EngineController(EngineService service) {
        this.service = service;
    }

    @PostMapping("/engine")
    public ResponseEntity<EngineResponse> process(@RequestBody @Valid EngineRequest request) {
        EngineResponse response = new EngineResponse(request.getPersonalCode());
        try {
            response = service.getCalculation(request);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (ResourceBadRequestException ex) {
            response.setMessage(ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (ResourceNotFoundException ex) {
            response.setMessage(ex.getMessage());
            return ResponseEntity.unprocessableEntity().body(response);
        }
    }
}