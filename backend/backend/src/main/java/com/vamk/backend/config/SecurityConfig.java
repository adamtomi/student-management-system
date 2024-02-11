package com.vamk.backend.config;

import com.vamk.backend.util.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security.authorizeHttpRequests(auth ->
                /*auth.requestMatchers("/api/students/{id}", "POST").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/course/{id}", "POST").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/courses", "PUT").hasAuthority(Role.ADMIN.name())*/
                        // auth.anyRequest().authenticated())
                        auth.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
