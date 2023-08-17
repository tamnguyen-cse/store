package com.demo.store.handler;

import com.demo.store.error.CommonError;
import com.demo.store.error.Error;
import com.demo.store.exception.BadRequestException;
import com.demo.store.exception.CommonException;
import com.demo.store.exception.ExternalFeignException;
import com.demo.store.exception.ForbiddenException;
import com.demo.store.exception.InternalFeignException;
import com.demo.store.exception.InternalServerException;
import com.demo.store.exception.NotFoundException;
import com.demo.store.exception.UnauthorizedException;
import com.demo.store.model.ErrorResponse;
import com.demo.store.service.ErrorService;
import com.demo.store.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class CustomizedExceptionHandler {

    @Autowired
    private ErrorService errorService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public final ErrorResponse handleBadRequestException(BadRequestException ex,
        WebRequest request) {
        log.error("BadRequestException", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = getErrorResponse(path, ex.getError(), ex.getParams());
        log.info("Response: " + response);
        return response;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public final ErrorResponse handleUnauthorizedException(UnauthorizedException ex,
        WebRequest request) {
        log.error("UnauthorizedException", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = getErrorResponse(path, ex.getError(), ex.getParams());
        log.info("Response: " + response);
        return response;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public final ErrorResponse handleForbiddenException(ForbiddenException ex, WebRequest request) {
        log.error("ForbiddenException", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = getErrorResponse(path, ex.getError(), ex.getParams());
        log.info("Response: " + response);
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException ex, WebRequest request) {
        log.error("NotFoundException" + ex);
        Object[] params = ex.getParams();
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = getErrorResponse(path, ex.getError(), ex.getParams());
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ErrorResponse handleInternalServerException(InternalServerException ex,
        WebRequest request) {
        log.error("InternalServerException", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = getErrorResponse(path, ex.getError(), ex.getParams());
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(InternalFeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalFeignException(InternalFeignException ex,
        WebRequest request) {
        log.error("InternalFeignException" + ex);
        String body = ex.getResponseBody();
        HttpStatus status = ex.getStatusCode();

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = JsonUtils.toObject(body, ErrorResponse.class);
        if (response == null) {
            response = errorService.getError(path, CommonError.INTERNAL_REQUEST_ERROR);
        } else {
            response.addLayerMetadata(errorService.getErrorWithMessage(path, null,
                CommonError.INTERNAL_REQUEST_ERROR));
        }

        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(ExternalFeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleExternalFeignException(ExternalFeignException ex,
        WebRequest request) {
        log.error("ExternalFeignException" + ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = errorService.getError(path, CommonError.EXTERNAL_REQUEST_ERROR);
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCommonException(CommonException ex, WebRequest request) {
        log.error("CommonException", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = getErrorResponse(path, ex.getError(), ex.getParams());
        log.info("Response: " + response);
        return response;
    }

    private ErrorResponse getErrorResponse(String path, Error error, Object[] params) {
        ErrorResponse errorResponse = null;
        if (params == null || params.length == 0) {
            errorResponse = errorService.getError(path, error);
        } else {
            errorResponse = errorService.getErrorWithParams(path, error, params);
        }
        return errorResponse;
    }

}
