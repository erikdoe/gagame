package com.mullekybernetik.gagame.util;

import java.util.Random;

public class StringGenerator {

    private static final Random defaultRandom = new Random();

    private final Random random;
    private final String charset;

    public StringGenerator(String charset) {
        this(charset, defaultRandom);
    }

    public StringGenerator(String charset, Random random) {
        this.charset = charset;
        this.random = random;
    }

    public String randomString(int length) {
        char[] generated = new char[length];
        for (int i = 0; i < length; i++) {
            int r = random.nextInt(charset.length());
            generated[i] = charset.charAt(r);
        }
        return new String(generated);
    }
}
