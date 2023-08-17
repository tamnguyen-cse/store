package com.demo.store.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    public final static RoundingMode ROUNDING_MODE = RoundingMode.UP;
    public final static int RATE_SCALE = 3;
    public final static int AMOUNT_SCALE = 2;
    public final static BigDecimal HUNDRED = new BigDecimal(100);

    private NumberUtils() {
    }

    /**
     * Random integer from 0 to max
     *
     * @return the random integer
     */
    public static Integer randomInteger(int max) {
        return (int) (Math.random() * max);
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
        return scaleDecimal(result);
    }

    /**
     * Round up big decimal value
     *
     * @return the sum BigDecimal
     */
    public static BigDecimal scaleDecimal(BigDecimal value) {
        return value.setScale(AMOUNT_SCALE, ROUNDING_MODE);
    }

    /**
     * Divide two big decimal
     *
     * @return the sum BigDecimal
     */
    public static BigDecimal divideDecimal(BigDecimal a, BigDecimal b) {
        return a.divide(b, AMOUNT_SCALE, ROUNDING_MODE);
    }

    public static BigDecimal divideDecimal(BigDecimal a, Integer b) {
        return a.divide(new BigDecimal(b), AMOUNT_SCALE, ROUNDING_MODE);
    }

    /**
     * Check the number is positive
     *
     * @return true | false
     */
    public static boolean isPositive(Integer number) {
        return number != null && number >= 0;
    }

    /**
     * Convert a percentage to rate by divide 100
     *
     * @return the rate
     */
    public static BigDecimal toRate(BigDecimal percentage) {
        return percentage.divide(HUNDRED, RATE_SCALE, ROUNDING_MODE);
    }

    /**
     * Convert a rate to percentage by multiply 100
     *
     * @return the percentage
     */
    public static BigDecimal toPercentage(BigDecimal rate) {
        return rate.multiply(HUNDRED);
    }

    /**
     * Combine price number and currency
     *
     * @return string price
     */
    public static String toPrice(BigDecimal value, String currency) {
        return currency + scaleDecimal(value);
    }

}
