package com.demo.store.service;

import com.demo.store.error.Error;
import com.demo.store.model.ErrorResponse;

public interface ErrorService {

    /**
     * Get error response from default errors
     *
     * @param error the error definition
     * @return ErrorResponse
     */
    public ErrorResponse getError(Error error);

    /**
     * Get error response
     *
     * @param path  the request path
     * @param error the error definition
     * @return ErrorResponse
     */
    public ErrorResponse getError(String path, Error error);

    /**
     * Create error response with message
     *
     * @param message the error message
     * @param error   the error definition
     * @return ErrorResponse
     */
    public ErrorResponse getErrorWithMessage(String message, Error error);

    /**
     * Create error response with message
     *
     * @param path    the request path
     * @param message the error message
     * @param error   the error definition
     * @return ErrorResponse
     */
    public ErrorResponse getErrorWithMessage(String path, String message, Error error);

    /**
     * Create error response with defined format
     *
     * @param error  the error definition
     * @param params the customized parameters
     * @return ErrorResponse
     */
    public ErrorResponse getErrorWithParams(Error error, Object... params);

    /**
     * Create error response with defined format
     *
     * @param path   the request path
     * @param error  the error definition
     * @param params the customized parameters
     * @return ErrorResponse
     */
    public ErrorResponse getErrorWithParams(String path, Error error, Object... params);

    /**
     * Create error response with defined format and customized message
     *
     * @param messageKey the key in message configuration
     * @param error      the error definition
     * @param params     the customized parameters
     * @return ErrorResponse
     */
    public ErrorResponse getErrorWithParamsMessage(String messageKey, Error error,
            Object... params);

    /**
     * Create error response with defined format and customized message
     *
     * @param path       the request path
     * @param messageKey the key in message configuration
     * @param error      the error definition
     * @param params     the customized parameters
     * @return ErrorResponse
     */
    public ErrorResponse getErrorWithParamsMessage(String path, String messageKey, Error error,
            Object... params);

}
