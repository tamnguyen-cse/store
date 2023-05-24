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
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 5522938341428311383L;

    private Error error;

    private Object[] params;

    public ForbiddenException(Error error) {
        this.error = error;
    }

}
