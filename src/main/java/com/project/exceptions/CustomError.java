package com.project.exceptions;

import com.project.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

@Getter
public class CustomError extends ErrorCode{

    private final String detailedMessage;


    CustomError(ErrorCode code, String detailedMessage) {
        super(code.getErrorCode(),code.getHttpStatus(), code.getMessage());
        this.detailedMessage = detailedMessage;
    }

    @SneakyThrows
    public void writeAsResponse(HttpServletResponse response, ObjectMapper objectMapper){
        response.setStatus(getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(this));
    }

    public static CustomError get(CustomException e, MessageService messageService){
        return new CustomError(e.getErrorCode(),
                messageService.get(e.getMessageKey().getName(),e.getMessageTokens()));
    }

}
