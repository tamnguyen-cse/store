package com.demo.store.common;

import com.demo.store.exception.InternalFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class InternalErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        String body = responseBody == null ? null : responseBody.toString();
        log.debug("Status: {}", responseStatus);
        log.debug("Feign response: {}", body);
        if (responseStatus.isError()) {
            return new InternalFeignException(responseStatus, body);
        }
        return null;
    }
}
