package com.demo.store.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.util.StringUtils;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {
    }

    static {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.findAndRegisterModules();
    }

    /**
     * Check the JSON format of string
     *
     * @param json String value
     * @return true: JSON object / false: not JSON object
     */
    public static boolean isJsonValid(String json) {
        boolean result = false;
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(json);
            result = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Convert Object From JSON.
     *
     * @param json  the string
     * @param clazz the class
     * @return object
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        if (String.class.equals(clazz)) {
            return (T) json;
        }

        T object = null;
        try {
            // Read string to object
            object = (T) mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    /**
     * Convert the object to object map.
     *
     * @param object the object
     * @return the object map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertToMap(Object object) {
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
    public static Map<String, String> convertToStringMap(Object object) {
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
     * Convert the object map to DTO.
     *
     * @param object the object
     * @param clazz  the DTO class
     * @return the DTO object
     */
    public static <T> T convertObjectToDTO(Object object, Class<T> clazz) {
        return mapper.convertValue(object, clazz);
    }

    /**
     * Convert JSON to object.
     *
     * @param jsonData      the JSON data
     * @param typeReference the type reference
     * @return the object
     */
    public static <T> T convertToObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        T object = null;
        try {
            // read JSON to object
            object = (T) mapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    /**
     * Convert the object to JSON.
     *
     * @param object the object
     * @return the JSON string
     */
    public static String convertToJson(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        if (object instanceof TextNode) {
            return ((TextNode) object).asText();
        }

        String json = null;
        try {
            // Write object to string
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * Get the compact form of pretty-printed JSON data (remove tab, space, break line, ...)
     *
     * @param jsonData the JSON data
     * @return the string
     */
    public static String trimJsonData(String json) {
        if (json == null) {
            return null;
        }

        String trimmingJson = json;
        JsonNode df;
        try {
            df = mapper.readValue(json, JsonNode.class);
            trimmingJson = df.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return trimmingJson;
    }

    /**
     * Get field from object base
     *
     * @param objectBase the object
     * @param fieldName  the field name
     * @return value string. Return null if this field is array or object
     */
    public static String getFieldFromObject(Object objectBase, String fieldName) {
        String value = null;
        try {
            Field field = objectBase.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = field.get(objectBase).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return value;
    }

}
