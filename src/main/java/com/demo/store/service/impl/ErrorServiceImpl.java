package com.demo.store.service.impl;

import com.demo.store.error.Error;
import com.demo.store.model.ErrorResponse;
import com.demo.store.service.ErrorService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ErrorServiceImpl implements ErrorService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public ErrorResponse getError(Error error) {
        return this.getError(null, error);
    }

    @Override
    public ErrorResponse getError(String path, Error error) {
        return ErrorResponse.builder().path(path).type(error.getType()).code(error.getCode())
                .message(getMessage(error.getType())).build();
    }

    @Override
    public ErrorResponse getErrorWithMessage(String message, Error error) {
        return this.getErrorWithMessage(null, message, error);
    }

    @Override
    public ErrorResponse getErrorWithMessage(String path, String message, Error error) {
        ErrorResponse response = ErrorResponse.builder().path(path).type(error.getType())
                .code(error.getCode()).message(message).build();
        return response;
    }

    @Override
    public ErrorResponse getErrorWithParams(Error error, Object... params) {
        return this.getErrorWithParams(null, error, params);
    }

    @Override
    public ErrorResponse getErrorWithParams(String path, Error error, Object... params) {
        return this.getErrorWithMessage(path, getMessage(error.toString(), params), error);
    }

    @Override
    public ErrorResponse getErrorWithParamsMessage(String messageKey, Error error,
            Object... params) {
        return getErrorWithMessage(null, getMessage(messageKey, params), error);
    }

    @Override
    public ErrorResponse getErrorWithParamsMessage(String path, String messageKey, Error error,
            Object... params) {
        return getErrorWithMessage(path, getMessage(messageKey, params), error);
    }

    private String getMessage(String messageKey, Object... params) {
        return messageSource.getMessage(messageKey, params, Locale.getDefault());
    }

}
