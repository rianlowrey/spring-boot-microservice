package com.company.microservice.domain;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class KeyGenerator {

    public static final char[] HAYSTACK = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private final int length;
    private final Random random;

    public KeyGenerator(int length, Random random) {
        this.length = length;
        this.random = random;
    }

    public KeyGenerator(int length) {
        this(length, new SecureRandom());
    }

    public KeyGenerator() {
        this(21);
    }

    public String generate() {
        final char[] buffer = new char[length];

        for (int i = 0; i < length; i++) {
            buffer[i] = HAYSTACK[random.nextInt(HAYSTACK.length)];
        }

        return new String(buffer);
    }
}
