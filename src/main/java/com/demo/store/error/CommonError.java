package com.demo.store.error;

public enum CommonError implements Error {

    /**
     * No error occurred
     *
     * @code 2xx
     */
    NO_ERROR("1000"),
    /**
     * Request body is missing
     *
     * @code 400
     */
    MISSING_REQUEST_BODY("1001"),
    /**
     * Request header is missing
     *
     * @code 400
     */
    MISSING_REQUEST_HEADER("1002"),
    /**
     * Request parameter is missing
     *
     * @code 400
     */
    MISSING_REQUEST_PARAM("1003"),
    /**
     * Path variable is missing
     *
     * @code 400
     */
    MISSING_PATH_VARIABLE("1004"),
    /**
     * Path variable is missing
     *
     * @code 400
     */
    MISSING_REQUEST_PART("1005"),
    /**
     * Method is not allowed
     *
     * @code 405
     */
    METHOD_NOT_ALLOWED("1006"),
    /**
     * Request body is invalid
     *
     * @code 400
     */
    INVALID_REQUEST_BODY("1007"),
    /**
     * Not found resource data
     *
     * @code 404
     */
    DATA_NOT_FOUND("1008"),
    /**
     * Not found resource API
     *
     * @code 404
     */
    API_NOT_FOUND("1009"),
    /**
     * Resource with field(s) is(are) null
     *
     * @var (0) the resource name
     * @var (1) the field name
     * @code 404
     */
    RESOURCE_NULL("1010"),
    /**
     * Not found data of the specified resource
     *
     * @var (0) the resource name
     * @var (1) the resource id
     * @code 404
     */
    RESOURCE_NOT_FOUND("1011"),
    /**
     * Processing DB transaction error
     *
     * @code 500
     */
    DB_OPERATION_ERROR("1996"),
    /**
     * Processing REST request/response error from internal system
     *
     * @code 500
     */
    INTERNAL_REQUEST_ERROR("1997"),
    /**
     * Processing REST request/response error from external system
     *
     * @code 500
     */
    EXTERNAL_REQUEST_ERROR("1998"),
    /**
     * Internal server error
     *
     * @code 500
     */
    INTERNAL_SERVER_ERROR("1999");

    private final String code;

    /**
     * Instantiates a new client error.
     *
     * @var code the error code
     */
    CommonError(String code) {
        this.code = code;
    }

    /**
     * Get Error from error code.
     *
     * @return the error corresponds to input code`
     * @var code the error code
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
