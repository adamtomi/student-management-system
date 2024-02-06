package com.vamk.backend.controller;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

import static com.vamk.backend.util.response.CommonResponses.internalServerError;

public abstract class AbstractController {
    private final Logger logger;

    protected AbstractController(Logger logger) {
        this.logger = logger;
    }

    protected ResponseEntity<?> wrap(Supplier<ResponseEntity<?>> generator) {
        try {
            return generator.get();
        } catch (Exception ex) {
            this.logger.error("An unexpected error occurred while processing this request.", ex);
            return internalServerError();
        }
    }
}
