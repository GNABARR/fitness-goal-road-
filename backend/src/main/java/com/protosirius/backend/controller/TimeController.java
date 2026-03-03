package com.protosirius.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/time")
public class TimeController {

    private static final Logger log = LoggerFactory.getLogger(TimeController.class);

    @GetMapping
    public Map<String, String> getCurrentTime() {
        log.info("GET /api/time");
        LocalDateTime now = LocalDateTime.now();
        return Map.of(
            "time", now.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            "date", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            "datetime", now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }
}
