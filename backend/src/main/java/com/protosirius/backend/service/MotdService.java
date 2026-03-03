package com.protosirius.backend.service;

import com.protosirius.backend.entity.Motd;
import com.protosirius.backend.repository.MotdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MotdService {

    private static final Logger log = LoggerFactory.getLogger(MotdService.class);

    private final MotdRepository motdRepository;

    public MotdService(MotdRepository motdRepository) {
        this.motdRepository = motdRepository;
    }

    public Motd getMotd() {
        log.info("Fetching MOTD");
        return motdRepository.findById(1L).orElseGet(() -> {
            log.info("No MOTD found, creating default");
            Motd defaultMotd = new Motd("Welcome to Proto Sirius!");
            defaultMotd.setId(1L);
            return motdRepository.save(defaultMotd);
        });
    }

    public Motd updateMotd(String message) {
        log.info("Updating MOTD with message: {}", message.trim());
        Motd motd = motdRepository.findById(1L).orElseGet(() -> {
            Motd newMotd = new Motd();
            newMotd.setId(1L);
            return newMotd;
        });
        motd.setMessage(message.trim());
        return motdRepository.save(motd);
    }
}
