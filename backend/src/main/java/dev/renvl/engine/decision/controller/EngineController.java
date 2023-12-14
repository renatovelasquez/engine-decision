package dev.renvl.engine.decision.controller;

import dev.renvl.engine.decision.dto.EngineRequest;
import dev.renvl.engine.decision.dto.EngineResponse;
import dev.renvl.engine.decision.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(service.getCalculation(request));
    }
}