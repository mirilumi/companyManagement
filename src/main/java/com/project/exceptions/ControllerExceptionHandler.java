package com.project.exceptions;

import com.project.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private final MessageService messageService;

    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<CustomError> handleAnyException(Exception ex) {
        log.error("Intercepted a Throwable", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomError(ErrorCode.SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<CustomError> handleCustomException(CustomException ex) {
        log.error("Intercepted an CustomException", ex);
        return respond(ex);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<CustomError> handleBadCredentials(BadCredentialsException ex) {
        log.error("Intercepted an error", ex);
        return respond(ErrorCode.UNAUTHORIZED, MessageKey.bad_credentials);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomError> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("Intercepted a MethodArgumentTypeMismatchException", e);
        return respond(ErrorCode.BAD_REQUEST, MessageKey.bad_request, e.getCause().getMessage());
    }

    @ExceptionHandler()
    public ResponseEntity<CustomError> handlConstraintViolation(Exception e) {
        if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            log.error("Intercepted a ConstraintViolationException", e);
            return respond(ErrorCode.CONFLICT,MessageKey.constraint_violation, e.getMessage());
        } else {
            return handleAnyException(e);
        }
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<CustomError> handlePayloadIssues(HttpMessageNotReadableException e) {
        log.error("Intercepted a HttpMessageNotReadableException");
        return respond(ErrorCode.BAD_REQUEST, MessageKey.bad_request, e.getCause().getMessage());
    }

    @ExceptionHandler(value = {org.springframework.security.access.AccessDeniedException.class})
    public ResponseEntity<CustomError> handleAccessDeniedException(Exception e) {
        log.error("Intercepted an AccessDeniedException", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    private ResponseEntity<CustomError> respond(ErrorCode errorCode, MessageKey messageKey, Object... messageTokens) {
       CustomError test =  new CustomError(errorCode, messageService.get(messageKey.getName(), messageTokens));
        return ResponseEntity.status(errorCode.getHttpStatus()).body(test);
    }

    private ResponseEntity<CustomError> respond(CustomException ex) {
        return respond(ex.getErrorCode(), ex.getMessageKey(), ex.getMessageTokens());
    }

}