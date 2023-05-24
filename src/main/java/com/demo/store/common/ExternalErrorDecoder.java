package com.demo.store.common;

import com.demo.store.common.Constant.Symbol;
import com.demo.store.exception.ExternalFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class ExternalErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url();
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        // Secure the API-key if yes by removing query parameters
        log.debug("Request URL: {}", requestUrl);
        String url = null;
        if (requestUrl.contains(Symbol.QUESTION_MARK)) {
            url = requestUrl.split(Symbol.QUESTION_MARK)[0];
        }
        if (responseStatus.isError()) {
            return new ExternalFeignException(url, HttpStatus.INTERNAL_SERVER_ERROR,
                    responseBody == null ? null : responseBody.toString());
        }
        return null;
    }
}
