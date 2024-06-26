package com.vamk.backend.controller;

import com.vamk.backend.model.User;
import com.vamk.backend.repository.UserRepository;
import com.vamk.backend.util.Role;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.vamk.backend.util.response.CommonResponses.badRequest;
import static com.vamk.backend.util.response.CommonResponses.ok;

@RestController
public class AuthController extends AbstractController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(LoggerFactory.getLogger(AuthController.class));
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> signup(
            @RequestBody User data
    ) {
        return wrap(() -> {
            String email = data.getEmail();
            String firstName = data.getFirstName();
            String lastName = data.getLastName();
            String password = data.getPassword();

            if (email == null || firstName == null || lastName == null || password == null) return badRequest("Some required fields are missing.");

            Optional<User> existing = this.userRepository.findByEmail(email);
            if (existing.isPresent()) return badRequest("This email address is taken.");

            String hashedPwd = this.passwordEncoder.encode(password);
            User user = this.userRepository.save(new User(firstName, lastName, email, hashedPwd, Role.STUDENT));
            return ok(user.getId());
        });
    }
}
