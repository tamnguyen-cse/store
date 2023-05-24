package com.demo.store.config;

import com.demo.store.common.InternalErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class InternalFeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new InternalErrorDecoder();
    }
}
