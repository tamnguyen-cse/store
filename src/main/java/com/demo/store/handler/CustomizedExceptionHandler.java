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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class CustomizedExceptionHandler extends CustomizedResponseExceptionHandler {

    @Autowired
    private ErrorService errorService;

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ErrorResponse> handleValidationException(BadRequestException ex,
            WebRequest request) {
        log.error("BadRequestException: ", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ErrorResponse error = getErrorResponse(path, ex.getError(), ex.getParams());
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(error,
                HttpStatus.BAD_REQUEST);
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ErrorResponse> handleAuthorizationException(
            UnauthorizedException ex, WebRequest request) {
        log.error("UnauthorizedException: ", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ErrorResponse error = getErrorResponse(path, ex.getError(), ex.getParams());
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(error,
                HttpStatus.UNAUTHORIZED);
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex,
            WebRequest request) {
        log.error("ForbiddenException: ", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ErrorResponse error = getErrorResponse(path, ex.getError(), ex.getParams());
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex,
            WebRequest request) {
        log.error("NotFoundException: " + ex);
        Object[] params = ex.getParams();
        String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ErrorResponse error = getErrorResponse(path, ex.getError(), ex.getParams());
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(InternalServerException.class)
    public final ResponseEntity<ErrorResponse> handleCommonException(InternalServerException ex,
            WebRequest request) {
        log.error("InternalServerException: {}", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ErrorResponse error = getErrorResponse(path, ex.getError(), ex.getParams());
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(error,
                HttpStatus.INTERNAL_SERVER_ERROR);
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(InternalFeignException.class)
    public ResponseEntity<ErrorResponse> handleInternalFeignException(InternalFeignException ex,
            WebRequest request) {
        log.error("InternalFeignException: " + ex);
        String body = ex.getResponseBody();
        HttpStatus status = ex.getStatusCode();

        String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ErrorResponse internalError = JsonUtils.convertToObject(body, ErrorResponse.class);
        if (internalError == null) {
            internalError = errorService.getError(path, CommonError.INTERNAL_REQUEST_ERROR);
        } else {
            internalError.addLayerMetadata(errorService.getErrorWithMessage(path, null,
                    CommonError.INTERNAL_REQUEST_ERROR));
        }
        ResponseEntity<ErrorResponse> response = ResponseEntity.status(status).body(internalError);
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(ExternalFeignException.class)
    public ResponseEntity<ErrorResponse> handleExternalFeignException(ExternalFeignException ex,
            WebRequest request) {
        log.error("ExternalFeignException: " + ex);

        String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ErrorResponse error = errorService.getError(path, CommonError.EXTERNAL_REQUEST_ERROR);

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(ex.getStatusCode())
                .body(error);
        log.info("Response: " + response);
        return response;
    }

    @ExceptionHandler(CommonException.class)
    public final ResponseEntity<ErrorResponse> handleCommonException(CommonException ex,
            WebRequest request) {
        log.error("CommonException: {}", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        ErrorResponse error = getErrorResponse(path, ex.getError(), ex.getParams());
        ResponseEntity<ErrorResponse> response = new ResponseEntity<>(error, ex.getStatus());
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
