package com.vamk.backend.security;

import com.vamk.backend.util.Hash;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SHA512PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        if (!(rawPassword instanceof String password)) throw new UnsupportedOperationException();
        return Hash.sha512(password);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
