package com.demo.store.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckResult {

    private Boolean isValid;

    @JsonIgnore
    private Map<String, Object> metadata = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getMap() {
        return metadata;
    }

    @JsonAnySetter
    void setDetails(String key, Object value) {
        metadata.put(key, value);
    }

}
