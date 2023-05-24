package com.demo.store.config;

import com.demo.store.common.ExternalErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class ExternalFeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ExternalErrorDecoder();
    }
}
