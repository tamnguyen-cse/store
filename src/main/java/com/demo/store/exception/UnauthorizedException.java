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
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -6076647895825582178L;

    private Error error;

    private Object[] params;

    public UnauthorizedException(Error error) {
        this.error = error;
    }

}
