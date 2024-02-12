package com.vamk.backend.security;

import com.vamk.backend.util.Hash;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SHA512PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return Hash.sha512(String.join(rawPassword, ""));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
