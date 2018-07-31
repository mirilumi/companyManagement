package com.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SingleValueHolder<T> {

    T value;

    public static <T> SingleValueHolder of(T value) {
        return new SingleValueHolder<>(value);
    }
}
