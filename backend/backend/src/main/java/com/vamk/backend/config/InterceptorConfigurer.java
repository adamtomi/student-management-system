package com.vamk.backend.config;

import com.vamk.backend.interceptor.RequestLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@EnableWebSecurity
public class InterceptorConfigurer implements WebMvcConfigurer {

    // Register interceptors
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLogger());
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security/*.authorizeHttpRequests(auth ->
                auth.requestMatchers("/api/course/", "POST")
                        .hasRole(Roles.ADMIN)
                        .requestMatchers("/api/students/", "POST")
                        .hasRole(Roles.ADMIN)
                        .requestMatchers("/api/*")
                        .hasAnyRole(Roles.ADMIN, Roles.STUDENT)
        )*/
                // .cors(new CorsConfigurer<>())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
