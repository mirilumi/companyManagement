package com.project.controller;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class DataPage<U> {

    public static final DataPage<?> EMPTY = new DataPage();

    private List<U> data = new ArrayList<>();
    private int totalPages = 0;
    private long totalElements = 0;
    private int pageNumber = 1;
    private int pageSize = 0;

    private DataPage() {
        // Intentionally Private
    }

    public static <U, V> DataPage newPage(Page<V> page, Function<V, U> mapper) {
        DataPage<U> dataPage = new DataPage<>();
        dataPage.data = page.getContent().stream().map(v -> mapper.apply(v)).collect(Collectors.toList());
        dataPage.totalPages = page.getTotalPages();
        dataPage.totalElements = page.getTotalElements();
        dataPage.pageNumber = page.getNumber() + 1;
        dataPage.pageSize = dataPage.data.size();
        return dataPage;
    }

    public static <T> DataPage<T> empty() {
        return new DataPage<T>();
    }


}
