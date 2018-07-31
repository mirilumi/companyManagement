package com.project.aspects;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Accessors(chain = true)
@Setter
public class GenericResponseDto {

    private List<String> messages = new ArrayList<>();

    public GenericResponseDto addMessage(String s) {
        messages.add(s);
        return this;
    }

    public static GenericResponseDto of(List<String> messages) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.getMessages().addAll(messages);
        return genericResponseDto;
    }
}
