package com.vamk.backend.auth;

import com.vamk.backend.model.AuthEntry;
import com.vamk.backend.model.User;
import com.vamk.backend.repository.AuthRepository;
import com.vamk.backend.repository.UserRepository;
import com.vamk.backend.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthProvider implements AuthenticationProvider {
    private static final String FAILURE_MSG = "Invalid email address or password";
    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthProvider(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        // Load login information
        Optional<AuthEntry> entry = this.authRepository.findByEmail(username);
        // If there is no entry with the provided email, reject.
        if (entry.isEmpty()) throw new BadCredentialsException(FAILURE_MSG);

        // Generate a SHA-512 hash for the password
        String password = Hash.sha512(authentication.getCredentials().toString());

        // Compare the two passwords. If they are not equal, reject.
        if (!entry.orElseThrow().getPassword().equals(password)) throw new BadCredentialsException(FAILURE_MSG);

        // Load roles for this user
        Optional<User> user = this.userRepository.findByEmail(username);
        if (user.isEmpty()) throw new BadCredentialsException("WTF");

        return new UsernamePasswordAuthenticationToken(username, password, List.of(user.orElseThrow().getRole()));

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
