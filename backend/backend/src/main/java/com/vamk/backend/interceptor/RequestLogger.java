package com.vamk.backend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * This interceptor is responsible for logging each request
 * (formatted as <method> <uri> -> <response status>) to the
 * console.
 */
@Component
public final class RequestLogger implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogger.class);
    private static final String OUT_FORMAT = "%s %s -> %s";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        LOGGER.info(OUT_FORMAT.formatted(request.getMethod(), request.getRequestURI(), response.getStatus()));
    }
}
