package com.demo.store.model;

import com.demo.store.error.Error;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    @JsonInclude(value = Include.NON_EMPTY)
    Map<String, Object> metadata;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
    private String code;
    private String type;
    private String message;

    public void addLayerMetadata(Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        String key = "layer" + (metadata.size() + 1);
        this.metadata.put(key, value);
    }

    public void ofError(Error error) {
        this.code = error.getCode();
        this.type = error.getType();
    }

}
