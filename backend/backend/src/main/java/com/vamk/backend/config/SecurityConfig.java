package com.vamk.backend.config;

import com.vamk.backend.util.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security.authorizeHttpRequests(auth ->
                auth.requestMatchers("/api/students/{id}", "POST").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/course/{id}", "POST").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/courses", "PUT").hasAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
