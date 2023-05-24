package com.demo.store.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class InternalFeignException extends RuntimeException {

    private static final long serialVersionUID = -5114789642799171553L;

    private HttpStatus statusCode;

    private String responseBody;

}
