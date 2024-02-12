package com.vamk.backend.util.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SHA512PasswordEncoder implements PasswordEncoder {

    /*
     * This function returns the hexadecimal representation
     * of the SHA-512 hash of the provided password.
     */
    @Override
    public String encode(CharSequence rawPassword) {
        if (!(rawPassword instanceof String password)) throw new UnsupportedOperationException();

        try {
            // Generate hash
            byte[] bytes = MessageDigest.getInstance("SHA-512")
                    .digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            // Convert each byte to it's hex representation. Totally not copied from StackOverflow.
            for (byte each : bytes) builder.append(Integer.toString((each & 0xFF) + 0x100, 16).substring(1));

            return builder.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
