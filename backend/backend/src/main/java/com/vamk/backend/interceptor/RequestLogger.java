package com.vamk.backend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.function.BiConsumer;

/*
 * This interceptor is responsible for logging each request
 * (formatted as <method> <uri> -> <response status>) to the
 * console.
 */
@Component
public final class RequestLogger implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogger.class);
    private static final BiConsumer<Integer, String> LOG_ACTION;
    private static final String OUT_FORMAT = "%s %s -> %s";

    static {
        /*
         * Select the appropriate logging level based on
         * the response status code.
         *
         * Success (2xx)      -> INFO
         * Client error (4xx) -> WARN
         * Server error (5xx) -> ERROR
         */
        LOG_ACTION = (status, msg) -> {
            if (status < 300) {
                LOGGER.info(msg);
            } else if (status < 500) {
                LOGGER.warn(msg);
            } else {
                LOGGER.error(msg);
            }
        };
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // Format the message
        String message = OUT_FORMAT.formatted(request.getMethod(), request.getRequestURI(), response.getStatus());
        LOG_ACTION.accept(response.getStatus(), message);
    }
}
