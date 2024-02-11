package com.vamk.backend.controller;

import com.vamk.backend.model.AuthEntry;
import com.vamk.backend.repository.AuthRepository;
import com.vamk.backend.util.Hash;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController extends AbstractController {
    private final AuthRepository authRepository;

    @Autowired
    public AuthController(AuthRepository authRepository) {
        super(LoggerFactory.getLogger(AuthController.class));
        this.authRepository = authRepository;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> signup(String username, String password) {
        throw new RuntimeException();
    }

    @PostMapping("/api/auth/signin")
    public ResponseEntity<?> signin(@RequestBody String email, @RequestBody String password) {
        return wrap(() -> {
            String hash = Hash.sha512(password);
            Optional<AuthEntry> entry = this.authRepository.findOne(Example.of(new AuthEntry(email, password)));

            throw new RuntimeException();
        });
    }
}
