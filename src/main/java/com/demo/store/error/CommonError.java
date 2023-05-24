package com.demo.store.error;

public enum CommonError implements Error {

    /**
     * No error occurred
     *
     * @category 2xx
     */
    NO_ERROR("1000"),

    /**
     * Request body is missing
     *
     * @category 400
     */
    MISSING_REQUEST_BODY("1001"),

    /**
     * Request header is missing
     *
     * @category 400
     */
    MISSING_REQUEST_HEADER("1002"),

    /**
     * Request parameter is missing
     *
     * @category 400
     */
    MISSING_QUERY_PARAM("1003"),

    /**
     * Request body is invalid
     *
     * @category 400
     */
    INVALID_REQUEST_BODY("1004"),

    /**
     * Not found resource data
     *
     * @category 404
     */
    DATA_NOT_FOUND("1005"),

    /**
     * Request data not matching with requirement
     *
     * @category 400
     */
    DATA_NOT_MATCH_REQUIREMENT("1006"),

    /**
     * Processing authentication error
     *
     * @category 403
     */
    DATA_PERMISSION_DENY("1007"),

    /**
     * Not found resource data
     *
     * @category 404
     */
    API_NOT_FOUND("1008"),

    /**
     * Resource with field(s) is(are) null
     *
     * @param (0) the resource name
     * @param (1) the field name
     * @category 404
     */
    RESOURCE_NULL("1009"),

    /**
     * Not found data of the specified resource
     *
     * @param (0) the resource name
     * @param (1) the resource id
     * @category 404
     */
    RESOURCE_NOT_FOUND("1010"),

    /**
     * Processing DB transaction error
     *
     * @category 500
     */
    DB_OPERATION_ERROR("1996"),

    /**
     * Processing REST request/response error from internal system
     *
     * @category 500
     */
    INTERNAL_REQUEST_ERROR("1997"),

    /**
     * Processing REST request/response error from external system
     *
     * @category 500
     */
    EXTERNAL_REQUEST_ERROR("1998"),

    /**
     * Internal server error
     *
     * @category 500
     */
    INTERNAL_SERVER_ERROR("1999");

    private final String code;

    /**
     * Instantiates a new client error.
     *
     * @param code the error code
     */
    private CommonError(String code) {
        this.code = code;
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

    /**
     * Get Error from error code.
     *
     * @param code the error code
     * @return the error corresponds to input code`
     */
    public static CommonError fromCode(String code) {
        CommonError[] errors = CommonError.values();
        for (CommonError error : errors) {
            if (error.getCode().equals(code)) {
                return error;
            }
        }
        return null;
    }
}
