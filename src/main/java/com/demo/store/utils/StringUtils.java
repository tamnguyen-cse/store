package com.demo.store.utils;

import static com.demo.store.common.Constant.Symbol.HYPHEN;
import static com.demo.store.common.Constant.Symbol.SPACE;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Objects;
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
     * Generate a random string with letters and numbers only based on length.
     *
     * @param length the length of the random string
     * @return the random string
     */
    public static String random(int length) {
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    /**
     * Normalize string
     *
     * @param input: the input raw string
     * @return normalized string.
     */
    public static String normalize(String input) {
        return Normalizer.isNormalized(input, Form.NFC) ? input
            : Normalizer.normalize(input, Form.NFC);
    }

    /**
     * Standardize string: normalize, trim and remove all consecutive spaces.
     *
     * @param input: the input raw string
     * @return parsed string.
     */
    public static String standardize(String input) {
        return isEmpty(input) ? null : normalize(input).trim().replaceAll("\\s+", SPACE);
    }

    /**
     * Simplify string: normalize, standardize and remove all special characters.
     *
     * @param input: the input string that need to be parsed.
     * @return parsed string.
     */
    public static String simplify(String input) {
        return isEmpty(input) ? null
            : standardize(normalize(input.replaceAll("[^a-zA-Z0-9\\s]", "")));
    }

    /**
     * Parse string name to slug
     *
     * @param input: the input string that need to be parsed.
     * @return parsed string.
     */
    public static String parseNameToSlug(String input) {
        input = isEmpty(input) ? null : input.replace(HYPHEN, SPACE);
        return Objects.requireNonNull(simplify(input)).replaceAll("\\s", HYPHEN).toLowerCase();
    }

}
