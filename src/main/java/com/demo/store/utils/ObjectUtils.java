package com.demo.store.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MappingContext;
import org.springframework.util.CollectionUtils;

public class ObjectUtils {

    private static final ModelMapper overrideMapper = new ModelMapper();
    private static final ModelMapper skipNullMapper = new ModelMapper();

    static {
        // override model
        overrideMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        overrideMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        // skip null model
        skipNullMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        skipNullMapper.getConfiguration().setSkipNullEnabled(true);
        skipNullMapper.getConfiguration().setAmbiguityIgnored(true);
        skipNullMapper.getConfiguration().setFieldMatchingEnabled(true);
        skipNullMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE);
        skipNullMapper.getConfiguration().setCollectionsMergeEnabled(false);
        skipNullMapper.getConfiguration()
            .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);

        Converter<List<Object>, List<Object>> listConverter = (MappingContext<List<Object>, List<Object>> context) -> {
            if (!CollectionUtils.isEmpty(context.getSource())) {
                return context.getSource();
            } else {
                return null;
            }
        };
        skipNullMapper.addConverter(listConverter);
    }

    private ObjectUtils() {
    }

    /**
     * Parse object to the type
     *
     * @param source the object
     * @param type   the object type
     * @return object
     */
    public static <T> T parse(Object source, Type type) {
        if (source == null) {
            return null;
        }
        return overrideMapper.map(source, type);
    }

    /**
     * Parse collection of object to the class type
     *
     * @param source the object
     * @param clazz  the class
     * @return object
     */
    public static <T> List<T> parse(Collection<?> source, Class<T> clazz) {
        if (CollectionUtils.isEmpty(source)) {
            return new ArrayList<>();
        }
        return source.stream().map(element -> overrideMapper.map(element, clazz))
            .collect(Collectors.toList());
    }

    /**
     * Parse object to the class type
     *
     * @param source the object
     * @param clazz  the class
     * @return object
     */
    public static <T> T parse(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        return overrideMapper.map(source, clazz);
    }

    /**
     * Merge override source to destination
     *
     * @param source      the object
     * @param destination the object
     * @return object
     */
    public static <T> T mergeOverride(Object source, T destination) {
        if (source == null) {
            return destination;
        }
        overrideMapper.map(source, destination);
        return destination;
    }

    /**
     * Merge skip null source to destination
     *
     * @param source      the object
     * @param destination the object
     * @return object
     */
    public static <T> T mergeSkipNull(Object source, T destination) {
        if (source == null) {
            return destination;
        }
        skipNullMapper.map(source, destination);
        return destination;
    }

}
