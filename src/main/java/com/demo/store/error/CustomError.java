package com.demo.store.error;

public enum CustomError implements Error {

    /**
     * No default currency exists
     *
     * @code 404
     */
    NO_DEFAULT_CURRENCY("1101");

    private final String code;

    /**
     * Instantiates a new client error.
     *
     * @param code the error code
     */
    CustomError(String code) {
        this.code = code;
    }

    /**
     * Get Error from error code.
     *
     * @param code the error code
     * @return the error corresponds to input code`
     */
    public static CustomError fromCode(String code) {
        CustomError[] errors = CustomError.values();
        for (CustomError error : errors) {
            if (error.getCode().equals(code)) {
                return error;
            }
        }
        return null;
    }

    /**
     * Get the error code.
     *
     * @return the error code
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Get the error type.
     *
     * @return the error type
     */
    @Override
    public String getType() {
        return this.toString();
    }

}
