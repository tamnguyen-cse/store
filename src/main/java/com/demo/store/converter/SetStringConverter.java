package com.demo.store.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Set;
import org.springframework.util.StringUtils;

@Converter
public class SetStringConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> list) {
        return StringUtils.collectionToCommaDelimitedString(list);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        return StringUtils.commaDelimitedListToSet(dbData);
    }

}
