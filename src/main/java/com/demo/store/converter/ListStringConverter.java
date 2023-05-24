package com.demo.store.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.StringUtils;

@Converter
public class ListStringConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return StringUtils.collectionToCommaDelimitedString(list);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.asList(StringUtils.commaDelimitedListToStringArray(dbData));
    }

}
