package com.project.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ErrorCode {
    public static final ErrorCode RESOURCE_NOT_FOUND = new ErrorCode(1000, HttpStatus.NOT_FOUND,"Resource not found");
    public static final ErrorCode CONFLICT = new ErrorCode(1001, HttpStatus.CONFLICT,"Conflicting Request");
    public static final ErrorCode FORBIDDEN = new ErrorCode(1002, HttpStatus.FORBIDDEN,"Forbidden action");
    public static final ErrorCode BAD_REQUEST = new ErrorCode(1003, HttpStatus.BAD_REQUEST,"Bad Request");
    public static final ErrorCode UNAUTHORIZED = new ErrorCode(1004, HttpStatus.UNAUTHORIZED,"Unauthorized Request");
    public static final ErrorCode EXPIRED_TOKEN = new ErrorCode(1008, HttpStatus.UNAUTHORIZED,"Expired Token");
    public static final ErrorCode STALE_DATA = new ErrorCode(1005, HttpStatus.CONFLICT,"Stale Data");
    public static final ErrorCode UN_PROCESSABLE = new ErrorCode(1005, HttpStatus.UNPROCESSABLE_ENTITY,"Un-processable Request");
    public static final ErrorCode SERVER_ERROR = new ErrorCode(1006, HttpStatus.INTERNAL_SERVER_ERROR,"Server Error");
    public static final ErrorCode EVENT_ERROR = new ErrorCode(1007, HttpStatus.INTERNAL_SERVER_ERROR,"Server Error");

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String message;

    protected ErrorCode(int errorCode, HttpStatus httpStatus,String message){
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
