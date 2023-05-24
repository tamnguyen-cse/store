package com.demo.store.exception;

import com.demo.store.error.Error;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@Builder
@AllArgsConstructor
@ResponseStatus(HttpStatus.FORBIDDEN)
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 5522958341428311383L;

    private Error error;

    private Object[] params;

    private HttpStatus status;

    public CommonException(Error error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }

}
