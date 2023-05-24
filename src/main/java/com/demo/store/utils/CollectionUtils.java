package com.demo.store.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.lang.Nullable;

public class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * Check empty condition for the collection
     *
     * @param collection the Collection to check
     * @return true | false
     */
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Check empty condition for the map
     *
     * @param map the Map to check
     * @return true | false
     */
    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * Check not empty condition for the collection
     *
     * @param collection the Collection to check
     * @return true | false
     */
    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Check not empty condition for the map
     *
     * @param map the Map to check
     * @return true | false
     */
    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Union two list
     *
     * @param list1 the first list
     * @param list2 the second list
     * @return the result list
     */
    public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> result = new HashSet<T>();
        result.addAll(list1);
        result.addAll(list2);
        return new ArrayList<T>(result);
    }

    /**
     * Intersection two list
     *
     * @param list1 the first list
     * @param list2 the second list
     * @return the result list
     */
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<T>();
        for (T t : list1) {
            if (list2.contains(t)) {
                result.add(t);
            }
        }
        return result;
    }

}
