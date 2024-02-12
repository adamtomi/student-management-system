package com.vamk.backend.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Hash {

    private Hash() {}

    /*
     * This function returns the hexadecimal representation
     * of the SHA-512 hash of the provided password.
     */
    public static String sha512(String password) {
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
}
