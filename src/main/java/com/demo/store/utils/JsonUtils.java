package com.demo.store.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.findAndRegisterModules();
    }

    private JsonUtils() {
    }

    /**
     * Validate if the string is in correct JSON format.
     *
     * @param json String value
     * @return true: JSON / false: not JSON
     */
    public static boolean isValid(String json) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(json);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert JSON to specific object.
     *
     * @param json  the string
     * @param clazz the class
     * @return object
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        if (String.class.equals(clazz)) {
            return (T) json;
        }

        try {
            // Read string to object
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert map as object to specific object.
     *
     * @param object the object
     * @param clazz  the DTO class
     * @return the DTO object
     */
    public static <T> T toObject(Object object, Class<T> clazz) {
        return mapper.convertValue(object, clazz);
    }

    /**
     * Convert JSON to type reference object.
     *
     * @param json          the JSON data
     * @param typeReference the type reference
     * @return the object
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            // read JSON to object
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert the object to object map.
     *
     * @param object the object
     * @return the object map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toObjectMap(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Map) {
            return (Map<String, Object>) object;
        }
        // Write object to map
        return mapper.convertValue(object, Map.class);
    }

    /**
     * Convert the object to string map.
     *
     * @param object the object
     * @return the string map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> toStringMap(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Map) {
            return (Map<String, String>) object;
        }
        // Write object to map
        return mapper.convertValue(object, Map.class);
    }

    /**
     * Convert the object to JSON.
     *
     * @param object the object
     * @return the JSON string
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        if (object instanceof TextNode) {
            return ((TextNode) object).asText();
        }

        try {
            // Write object to string
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the compact form of pretty-printed JSON data (remove tab, space, break line, ...)
     *
     * @param json the JSON data
     * @return the string
     */
    public static String trim(String json) {
        if (json == null) {
            return null;
        }

        JsonNode df;
        try {
            df = mapper.readValue(json, JsonNode.class);
            return df.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
