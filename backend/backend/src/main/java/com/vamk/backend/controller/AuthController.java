package com.vamk.backend.controller;

import com.vamk.backend.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController extends AbstractController {
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        super(LoggerFactory.getLogger(AuthController.class));
        this.userRepository = userRepository;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> signup(String username, String password) {
        throw new RuntimeException();
    }

    @PostMapping("/api/auth/signin")
    public ResponseEntity<?> signin() {
        throw new RuntimeException();
    }
}
