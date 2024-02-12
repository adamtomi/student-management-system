package com.vamk.backend.auth;

import com.vamk.backend.model.User;
import com.vamk.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthProvider implements AuthenticationProvider {
    private static final String INVALID_CREDENTIALS_MSG = "Invalid email address or password.";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        // Find user by email address.
        Optional<User> userOpt = this.userRepository.findByEmail(username);
        // If there is no entry with the provided email, reject.
        if (userOpt.isEmpty()) throw new BadCredentialsException(INVALID_CREDENTIALS_MSG);

        User user = userOpt.orElseThrow();
        // Generate a SHA-512 hash for the password
        String password = this.passwordEncoder.encode(authentication.getCredentials().toString());

        // Compare the two passwords. If they are not equal, reject.
        if (!user.getPassword().equals(password)) throw new BadCredentialsException(INVALID_CREDENTIALS_MSG);

        return new UsernamePasswordAuthenticationToken(username, password, List.of(user.getRole()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
