package com.demo.store.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    private NumberUtils() {
    }

    public final static RoundingMode ROUNDING_MODE = RoundingMode.UP;
    public final static int SCALE = 2;

    /**
     * Sum 2 Integer Objects
     *
     * @return the sum Integer
     */
    public static Integer sumInteger(Integer a, Integer b) {
        return Integer.valueOf(a.intValue() + b.intValue());
    }

    /**
     * Subtract 2 Integer Objects
     *
     * @return the sum Integer
     */
    public static Integer subtractInteger(Integer a, Integer b) {
        return Integer.valueOf(a.intValue() - b.intValue());
    }

    /**
     * Multiply 2 Integer Objects
     *
     * @return the sum Integer
     */
    public static Integer multiplyInteger(Integer a, Integer b) {
        return Integer.valueOf(a.intValue() * b.intValue());
    }

    /**
     * Divide 2 Integer Objects
     *
     * @return the sum Integer
     */
    public static Integer divideInteger(Integer a, Integer b) {
        return Integer.valueOf(a.intValue() / b.intValue());
    }

    /**
     * Modulo 2 Integer Objects
     *
     * @return the sum Integer
     */
    public static Integer moduloInteger(Integer a, Integer b) {
        return Integer.valueOf(a.intValue() % b.intValue());
    }

    /**
     * Reverse integer from positive to negative and vice versa
     *
     * @return the reversed Integer
     */
    public static Integer reverseInteger(Integer a) {
        return Integer.valueOf(a.intValue() * -1);
    }

    /**
     * New big decimal with round up value
     *
     * @return the sum BigDecimal
     */
    public static BigDecimal newDecimal(double value) {
        return newDecimal(String.valueOf(value));
    }

    /**
     * New big decimal with round up value
     *
     * @return the sum BigDecimal
     */
    public static BigDecimal newDecimal(String value) {
        BigDecimal result = new BigDecimal(value);
        return scaleBigDecimal(result);
    }

    /**
     * Round up big decimal value
     *
     * @return the sum BigDecimal
     */
    public static BigDecimal scaleBigDecimal(BigDecimal value) {
        return value.setScale(SCALE, ROUNDING_MODE);
    }

    /**
     * Check the number is positive
     *
     * @return true | false
     */
    public static boolean isPositiveNumber(Integer number) {
        return number != null && number >= 0;
    }

    /**
     * Check the number is negative
     *
     * @return true | false
     */
    public static boolean isNegativeNumber(Integer number) {
        return number != null && number <= 0;
    }

    /**
     * Combine price number and currency
     *
     * @return string price
     */
    public static String combinePrice(BigDecimal price, String currency) {
        return currency + scaleBigDecimal(price);
    }

}
