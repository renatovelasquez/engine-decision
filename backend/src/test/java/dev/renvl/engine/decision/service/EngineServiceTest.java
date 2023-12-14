package dev.renvl.engine.decision.service;

import dev.renvl.engine.decision.dto.EngineRequest;
import dev.renvl.engine.decision.dto.EngineResponse;
import dev.renvl.engine.decision.model.Profile;
import dev.renvl.engine.decision.repository.ProfileRepository;
import dev.renvl.engine.decision.utils.UnprocessableEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EngineServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private EngineServiceImpl engineService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCalculation() {
        EngineRequest engineRequest = new EngineRequest("123456", 4000, 12);
        Profile profile = new Profile("123456", "SEGMENT_2", 300);
        when(profileRepository.findById(any())).thenReturn(Optional.of(profile));

        EngineResponse engineResponse = engineService.getCalculation(engineRequest);

        assertEquals(engineResponse.getLoanAmount(), 3600);
        assertEquals(engineRequest.getPersonalCode(), engineResponse.getPersonalCode());
    }

    @Test
    public void testGetCalculationWithNoCreditModifier() {
        EngineRequest engineRequest = new EngineRequest("123456", 4000, 12);
        Profile profile = new Profile("123456", "DEBT", null);
        when(profileRepository.findById(any())).thenReturn(Optional.of(profile));

        assertThrows(UnprocessableEntityException.class, () -> engineService.getCalculation(engineRequest));
    }
}
