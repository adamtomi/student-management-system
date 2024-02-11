package com.vamk.backend.controller;

import com.vamk.backend.model.User;
import com.vamk.backend.repository.UserRepository;
import com.vamk.backend.util.Hash;
import com.vamk.backend.util.Role;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.vamk.backend.util.response.CommonResponses.badRequest;
import static com.vamk.backend.util.response.CommonResponses.ok;

@RestController
public class AuthController extends AbstractController {
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        super(LoggerFactory.getLogger(AuthController.class));
        this.userRepository = userRepository;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> signup(
            @RequestBody String firstName,
            @RequestBody String lastName,
            @RequestBody String email,
            @RequestBody String password
    ) {
        return wrap(() -> {
            Optional<User> existing = this.userRepository.findByEmail(email);
            if (existing.isPresent()) return badRequest("This email address is taken.");

            String hashedPwd = Hash.sha512(password);
            User user = this.userRepository.save(new User(firstName, lastName, email, hashedPwd, Role.STUDENT));
            return ok(user.getId());
        });
    }
}
