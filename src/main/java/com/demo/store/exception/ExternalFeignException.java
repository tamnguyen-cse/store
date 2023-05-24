package com.demo.store.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ExternalFeignException extends RuntimeException {

    private static final long serialVersionUID = 8141015187087860102L;

    private String requestUrl;

    private HttpStatus statusCode;

    private String responseBody;

}
