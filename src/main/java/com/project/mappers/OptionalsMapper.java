package com.project.mappers;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptionalsMapper {

    public String resolve(Optional<String> optional) {
        return optional.orElse(null);
    }

    public Optional<String> resolve(String value) {
        return Optional.ofNullable(value);
    }
}