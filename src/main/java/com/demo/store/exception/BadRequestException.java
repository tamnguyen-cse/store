package com.demo.store.exception;

import com.demo.store.error.Error;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 5522938341428311383L;

    private Error error;

    private Object[] params;

    public BadRequestException(Error error) {
        this.error = error;
    }

}
