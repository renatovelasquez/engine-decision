package dev.renvl.engine.decision.service;

import dev.renvl.engine.decision.dto.EngineRequest;
import dev.renvl.engine.decision.dto.EngineResponse;
import dev.renvl.engine.decision.model.Profile;
import dev.renvl.engine.decision.model.ProfileType;
import dev.renvl.engine.decision.repository.ProfileRepository;
import dev.renvl.engine.decision.utils.ResourceBadRequestException;
import dev.renvl.engine.decision.utils.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EngineServiceImpl implements EngineService {

    private final ProfileRepository profileRepository;
    private static final Logger log = LoggerFactory.getLogger(EngineServiceImpl.class);

    public EngineServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public EngineResponse getCalculation(EngineRequest engineRequest) throws EntityNotFoundException {
        EngineResponse engineResponse = new EngineResponse(engineRequest.getPersonalCode());
        Profile profile = profileRepository.getReferenceById(engineResponse.getPersonalCode());

        final int MIN_AMOUNT = 2000;
        final int MAX_AMOUNT = 10000;
        int MIN_PERIOD = 12;
        final int MAX_PERIOD = 60;

        if ((engineRequest.getLoanAmount() < MIN_AMOUNT || MAX_AMOUNT < engineRequest.getLoanAmount()) ||
                (engineRequest.getLoanPeriod() < MIN_PERIOD || MAX_PERIOD < engineRequest.getLoanPeriod()))
            throw new ResourceBadRequestException("The request was out of range.");

        MIN_PERIOD = engineRequest.getLoanPeriod();

        //Not found credit modifier for the customer
        if (profile.getType().equals(ProfileType.DEBT.name()))
            throw new ResourceNotFoundException("It's not possible to process due the customer has a Debt.");

        int amount = 0;
        int period = 0;
        double score = Double.MAX_VALUE;

        for (int i = MIN_AMOUNT; i <= MAX_AMOUNT; i++) {
            for (int j = MIN_PERIOD; j <= MAX_PERIOD; j++) {
                double currentScore = score(profile.getCreditModifier(), i, j);
                if (currentScore >= 1 && Math.abs(currentScore - 1) < Math.abs(score - 1)) {
                    amount = i;
                    period = j;
                    score = currentScore;
                }
            }
        }

        // Final values
        log.info("Customer={} Amount={} Period={} Score={}", engineRequest.getPersonalCode(), amount, period, score);

        engineResponse.setLoanAmount(amount);
        engineResponse.setLoanPeriod(period);
        engineResponse.setMessage("Suitable amount " + amount + " and " + period + " months");
        return engineResponse;
    }

    private double score(double creditModifier, int amount, int period) {
        return (creditModifier / amount) * period;
    }
}
