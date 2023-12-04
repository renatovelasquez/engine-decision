package dev.renvl.engine.decision;

import dev.renvl.engine.decision.model.ProfileType;
import dev.renvl.engine.decision.repository.ProfileRepository;
import dev.renvl.engine.decision.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EngineDecisionApplication {

    private static final Logger log = LoggerFactory.getLogger(EngineDecisionApplication.class);

    @Bean
    public CommandLineRunner initDatabase(ProfileRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Profile("49002010965", ProfileType.DEBT.name(), null)));
            log.info("Preloading " + repository.save(new Profile("49002010976", ProfileType.SEGMENT_1.name(), 100)));
            log.info("Preloading " + repository.save(new Profile("49002010987", ProfileType.SEGMENT_2.name(), 300)));
            log.info("Preloading " + repository.save(new Profile("49002010998", ProfileType.SEGMENT_3.name(), 1000)));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(EngineDecisionApplication.class, args);
    }
}
