package dev.renvl.engine.decision.service;

import dev.renvl.engine.decision.dto.EngineRequest;
import dev.renvl.engine.decision.dto.EngineResponse;


public interface EngineService {
    EngineResponse getCalculation(EngineRequest engineRequest);

}
