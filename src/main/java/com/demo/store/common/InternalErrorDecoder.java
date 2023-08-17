package com.demo.store.common;

import com.demo.store.exception.InternalFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

@Slf4j
public class InternalErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        log.debug("Request URL: {}", response.request().url());
        log.debug("Request Headers: {}", response.request().headers());
        String body = null;
        try {
            body = responseBody == null ? null
                : IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Response Status: {}", responseStatus);
        log.debug("Response Body: {}", body);
        if (responseStatus.isError()) {
            return new InternalFeignException(responseStatus, body);
        }
        return null;
    }
}
