package dev.renvl.engine.decision.service;

import dev.renvl.engine.decision.dto.EngineRequest;
import dev.renvl.engine.decision.dto.EngineResponse;
import dev.renvl.engine.decision.model.Profile;
import dev.renvl.engine.decision.model.ProfileType;
import dev.renvl.engine.decision.repository.ProfileRepository;
import dev.renvl.engine.decision.utils.NoDataFoundException;
import dev.renvl.engine.decision.utils.UnprocessableEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EngineServiceImpl implements EngineService {

    private final ProfileRepository profileRepository;
    final int MIN_AMOUNT = 2000;
    final int MAX_AMOUNT = 10000;
    final int MAX_PERIOD = 60;
    private static final Logger log = LoggerFactory.getLogger(EngineServiceImpl.class);

    public EngineServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public EngineResponse getCalculation(EngineRequest engineRequest) {

        Profile profile = profileRepository.findById(engineRequest.getPersonalCode())
                .orElseThrow(() -> new NoDataFoundException("No profile found."));

        //Not found credit modifier for the customer
        if (profile.getType().equals(ProfileType.DEBT.name()))
            throw new UnprocessableEntityException("It's not possible to process due the customer has a Debt.");

        return minimalGapScore(engineRequest.getPersonalCode(), engineRequest.getLoanPeriod(), profile.getCreditModifier());
    }

    private double score(double creditModifier, int amount, int period) {
        return (creditModifier / amount) * period;
    }

    private EngineResponse minimalGapScore(String code, int requestPeriod, double creditModifier) {
        int amount = 0;
        int period = 0;
        double score = Double.MAX_VALUE;

        for (int i = MIN_AMOUNT; i <= MAX_AMOUNT; i++) {
            for (int j = requestPeriod; j <= MAX_PERIOD; j++) {
                double currentScore = score(creditModifier, i, j);
                if (currentScore >= 1 && Math.abs(currentScore - 1) < Math.abs(score - 1)) {
                    amount = i;
                    period = j;
                    score = currentScore;
                }
            }
        }
        log.info("Customer={} Amount={} Period={} Score={}", code, amount, period, score);

        // Final values
        EngineResponse engineResponse = new EngineResponse();
        engineResponse.setPersonalCode(code);
        engineResponse.setLoanAmount(amount);
        engineResponse.setLoanPeriod(period);
        engineResponse.setMessage("Suitable amount " + amount + " and " + period + " months");

        return engineResponse;
    }
}
