package com.demo.store.handler;

import com.demo.store.error.CommonError;
import com.demo.store.model.ErrorResponse;
import com.demo.store.service.ErrorService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestController
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ErrorService errorService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ErrorResponse handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());

        // Handle error message
        String message = ex.getName() + " should be of type " +
            Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = errorService.getErrorWithMessage(path, message,
            CommonError.INVALID_REQUEST_BODY);
        log.info("OUT - response = {}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ConstraintViolationException.class})
    public ErrorResponse handleConstraintViolation(
        ConstraintViolationException ex, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());

        // Handle error metadata
        Map<String, Object> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(violation.getRootBeanClass().getSimpleName() + " "
                + violation.getPropertyPath(), violation.getMessage());
        }

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse response = errorService.getErrorWithMessage(path, ex.getMessage(),
            CommonError.DB_OPERATION_ERROR);
        response.setMetadata(errors);

        log.info("OUT - response = {}", response);
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());

        // Handle error message
        String message =
            ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
                + ex.getRequiredType();
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = errorService.getErrorWithMessage(path, message,
            CommonError.INVALID_REQUEST_BODY);

        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        log.info("OUT - response = {}", response);
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = errorService.getError(path, CommonError.API_NOT_FOUND);
        error.setPath(path);

        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        log.info("OUT - response = {}", response);
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = errorService.getErrorWithMessage(path, ex.getMessage(),
            CommonError.METHOD_NOT_ALLOWED);
        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        log.info("OUT - response = {}", response);
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = errorService.getErrorWithMessage(path, ex.getMessage(),
            CommonError.MISSING_PATH_VARIABLE);
        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        log.info("OUT - response = {}", response);
        return response;
    }


    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
        ServletRequestBindingException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = errorService.getErrorWithMessage(path, ex.getMessage(),
            CommonError.MISSING_REQUEST_HEADER);
        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        log.info("OUT - response = {}", response);
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
        MissingServletRequestPartException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = errorService.getErrorWithMessage(path, ex.getMessage(),
            CommonError.MISSING_REQUEST_PART);
        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        log.info("OUT - response = {}", response);
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = errorService.getErrorWithMessage(path, ex.getMessage(),
            CommonError.MISSING_REQUEST_PARAM);
        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        log.info("OUT - response = {}", response);
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = errorService.getErrorWithMessage(path, ex.getMessage(),
            CommonError.MISSING_REQUEST_BODY);
        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        log.info("OUT - response = {}", response);
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {
        log.info(ex.getClass().getSimpleName());
        log.error("Exception: {}", ex.getLocalizedMessage());

        // Handle error metadata
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = errorService.getErrorWithMessage(path, ex.getClass().getSimpleName(),
            CommonError.INVALID_REQUEST_BODY);
        error.setMetadata(errors);

        ResponseEntity<Object> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        log.info("OUT - response = {}", response);
        return response;
    }

    @Order
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Exception", ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return errorService.getError(path, CommonError.INTERNAL_SERVER_ERROR);
    }

}
