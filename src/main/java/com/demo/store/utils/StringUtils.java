package com.demo.store.utils;

import com.demo.store.common.Constant.Symbol;
import java.text.Normalizer;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;

public class StringUtils {

    private StringUtils() {
    }

    /**
     * Check if string is empty
     *
     * @param input: the input string
     * @return boolean.
     */
    public static boolean isEmpty(String input) {
        return (input == null || "".equals(input));
    }

    /**
     * Check if string is empty
     *
     * @param input: the input string
     * @return boolean.
     */
    public static boolean isNotEmpty(String input) {
        return !StringUtils.isEmpty(input);
    }

    /**
     * Normalize string
     *
     * @param input: the input string
     * @return normalized string.
     */
    public static String normalize(String input) {
        if (!Normalizer.isNormalized(input, Normalizer.Form.NFC)) {
            return Normalizer.normalize(input, Normalizer.Form.NFC);
        }
        return input;
    }

    /**
     * Normalize string value
     *
     * @param input: the input string that need to be parsed.
     * @return parsed string.
     */
    public static String normalizeString(String input) {
        return isEmpty(input) ? null : input.trim().replaceAll("\\s+", Symbol.SPACE);
    }

    /**
     * Parse to string URL
     *
     * @param input: the input string that need to be parsed.
     * @return parsed string.
     */
    public static String parseToUrlString(String input) {
        return isEmpty(input) ? null : normalize(input).replaceAll("[^a-zA-Z0-9\\s]", "").trim()
                .replaceAll("\\s+", Symbol.SPACE).replaceAll("\\s", Symbol.HYPHEN);
    }

    /**
     * Parse string name to slug
     *
     * @param input: the input string that need to be parsed.
     * @return parsed string.
     */
    public static String parseToSlug(String input) {
        input = isEmpty(input) ? null : input.replace(Symbol.HYPHEN, Symbol.SPACE);
        return parseToUrlString(input).toLowerCase();
    }

    /**
     * Generate a random string with letters and numbers only base on length.
     *
     * @param length the length of random string
     * @return the random string
     */
    public static String randomString(int length) {
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    /**
     * Generate OTP code.
     *
     * @return the string OTP code
     */
    public static String generateOTP() {
        Random rnd = new Random();
        int randomOTP = rnd.nextInt(999999);
        return String.format("%06d", randomOTP);
    }

    /**
     * Generate code that can be redeemed.
     *
     * @return the string OTP code
     */
    public static String generateCode() {
        StringBuilder result = new StringBuilder();
        String[] genAuto = UUID.randomUUID().toString().split(Symbol.HYPHEN);
        result.append(genAuto[0]);
        return result.toString().toUpperCase();
    }

}
