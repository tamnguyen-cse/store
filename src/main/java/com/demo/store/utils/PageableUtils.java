package com.demo.store.utils;

import com.demo.store.model.ContentPage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

    public static final Integer DF_PAGE_NUMBER = 0;
    public static final Integer DF_PAGE_SIZE = 10;
    public static final String DF_SORT_COLUMN = "id";

    public static Integer getDefaultPage(Integer i) {
        return NumberUtils.isPositive(i) ? i : DF_PAGE_NUMBER;
    }

    public static Integer getDefaultSize(Integer i) {
        return NumberUtils.isPositive(i) ? i : DF_PAGE_SIZE;
    }

    public static String getDefaultSortBy(String field) {
        return StringUtils.isEmpty(field) ? DF_SORT_COLUMN : field;
    }

    /**
     * Parse the page result to the content page object
     *
     * @param results the page result
     * @param clazz   the class of the content
     * @return Pageable
     */
    public static <E, D> ContentPage<D> toContentPage(Page<E> results, Class<D> clazz) {
        // Parsing data to content page
        int size = results.getSize();
        int number = results.getNumber();
        int totalPages = results.getTotalPages();
        long totalElements = results.getTotalElements();
        int numberOfElements = results.getNumberOfElements();
        List<D> data = ObjectUtils.parse(results.getContent(), clazz);
        return new ContentPage<>(size, number, totalPages, totalElements, numberOfElements, data);
    }

    /**
     * Create Pageable
     *
     * @param page   the page number
     * @param size   the page size
     * @param sortBy the sort column
     * @param isAsc  the sort direction (true: ASC, false: DESC)
     * @return Pageable
     */
    public static Pageable create(Integer page, Integer size, String sortBy, boolean isAsc) {
        page = PageableUtils.getDefaultPage(page);
        size = PageableUtils.getDefaultSize(size);
        sortBy = PageableUtils.getDefaultSortBy(sortBy);

        // ASC or DESC
        if (StringUtils.isEmpty(sortBy)) {
            return PageRequest.of(page, size);
        } else {
            var sort = isAsc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            return PageRequest.of(page, size, sort);
        }
    }

    /**
     * Create Pageable with default sort is descending
     *
     * @param page   the page number
     * @param size   the page size
     * @param sortBy the sort column
     * @return Pageable
     */
    public static Pageable create(Integer page, Integer size, String sortBy) {
        return PageableUtils.create(page, size, sortBy, false);
    }

    /**
     * Create Pageable with default size
     *
     * @param page   the page number
     * @param sortBy the sort column
     * @return Pageable
     */
    public static Pageable createWithDefaultSize(Integer page, String sortBy, boolean isAsc) {
        return PageableUtils.create(page, null, sortBy, isAsc);
    }

    /**
     * Create Pageable with default size and sort is descending
     *
     * @param page   the page number
     * @param sortBy the sort column
     * @return Pageable
     */
    public static Pageable createWithDefaultSize(Integer page, String sortBy) {
        return PageableUtils.create(page, null, sortBy, false);
    }

    /**
     * Create Pageable without sort
     *
     * @param page the page number
     * @return Pageable
     */
    public static Pageable createWithoutSort(Integer page, Integer size) {
        page = PageableUtils.getDefaultPage(page);
        size = PageableUtils.getDefaultSize(size);
        return PageRequest.of(page, size);
    }

    /**
     * Create Pageable with default size and no sort
     *
     * @param page the page number
     * @return Pageable
     */
    public static Pageable createWithoutSort(Integer page) {
        return PageableUtils.createWithoutSort(page, null);
    }

}
