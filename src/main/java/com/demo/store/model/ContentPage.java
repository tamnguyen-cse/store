package com.demo.store.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentPage<T> {

    private int size;
    private int number;
    private int totalPages;
    private long totalElements;
    private int numberOfElements;
    private List<T> content;

}
