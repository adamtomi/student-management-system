package com.vamk.backend.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebFilter("/api/*")
public class SetHeadersFilter implements Filter {
    private static final Map<String, String> CORS_HEADERS = Map.of(
            "Access-Control-Allow-Origin", "*",
            "Access-Control-Allow-Methods", "GET, OPTIONS",
            "Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token, X-Csrf-Token, WWW-Authenticate, Authorization",
            "Access-Control-Allow-Credentials", "false",
            "Access-Control-Max-Age", "3600"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Set CORS headers on the response object
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        CORS_HEADERS.forEach(httpServletResponse::setHeader);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
