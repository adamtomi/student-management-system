package com.vamk.backend.controller;

import com.vamk.backend.util.response.Response;
import org.slf4j.Logger;

import java.util.function.Supplier;

import static com.vamk.backend.util.response.CommonResponses.internalServerError;

public abstract class AbstractController {
    private final Logger logger;

    protected AbstractController(Logger logger) {
        this.logger = logger;
    }

    protected Response wrap(Supplier<Response> generator) {
        try {
            return generator.get();
        } catch (Exception ex) {
            this.logger.error("An unexpected error occurred while processing this request.", ex);
            return internalServerError();
        }
    }
}
