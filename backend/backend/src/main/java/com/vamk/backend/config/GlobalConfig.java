package com.vamk.backend.config;

import com.vamk.backend.auth.AuthProvider;
import com.vamk.backend.interceptor.RequestLogger;
import com.vamk.backend.util.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@Configuration
@EnableWebSecurity
public class GlobalConfig implements WebMvcConfigurer {

    // Register interceptors
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLogger());
    }

    @Bean
    UserDetailsService userDetailService() {
        InMemoryUserDetailsManager userService = new InMemoryUserDetailsManager();
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("password"))
                .authorities("read")
                .build();
        userService.createUser(user);
        return userService;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    AuthenticationProvider authProvider() {
        return new AuthProvider();
    }*/

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        /*return security.authorizeHttpRequests(auth ->
                /*auth.requestMatchers("/api/course/", "POST")
                        .hasRole(Role.ADMIN.name())
                        .requestMatchers("/api/students/", "POST")
                        .hasRole(Role.ADMIN.name())
                        .requestMatchers("/api/*")
                        .hasAnyRole(Role.ADMIN.name(), Role.STUDENT.name())
                        .anyRequest().authenticated()
                auth.anyRequest().authenticated()
        )
                //.csrf(AbstractHttpConfigurer::disable)
                //.httpBasic(Customizer.withDefaults())
                .build();*/
        return security.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
