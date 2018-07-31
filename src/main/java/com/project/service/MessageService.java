package com.project.service;

import com.project.exceptions.MessageKey;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageSource messages;

    public String get(String key, Object... tokens){
        return messages.getMessage(key,tokens, Locale.getDefault());
    }

    public String get(MessageKey messageKey, Object... tokens){
        return messages.getMessage(messageKey.getName(),tokens, Locale.getDefault());
    }
}
