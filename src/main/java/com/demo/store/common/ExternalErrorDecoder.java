package com.demo.store.common;

import com.demo.store.common.Constant.Symbol;
import com.demo.store.exception.ExternalFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

@Slf4j
public class ExternalErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url();
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        log.debug("Request URL: {}", requestUrl);
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
        // Secure the API-key if yes by removing query parameters
        if (requestUrl.contains(Symbol.QUESTION_MARK)) {
            requestUrl = requestUrl.split(Symbol.QUESTION_MARK)[0];
        }
        if (responseStatus.isError()) {
            return new ExternalFeignException(requestUrl, HttpStatus.INTERNAL_SERVER_ERROR, body);
        }
        return null;
    }
}
