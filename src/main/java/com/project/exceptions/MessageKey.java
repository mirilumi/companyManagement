package com.project.exceptions;

import lombok.Getter;

@Getter
public class MessageKey {
    private final String name;
    private final int numTokens;

    private MessageKey(String key, int numTokens){
        this.name = key;
        this.numTokens = numTokens;
    }

    public static final MessageKey entity_not_found = new MessageKey("entity_not_found",1);
    public static final MessageKey bad_credentials = new MessageKey("bad_credentials",0);
    public static final MessageKey bad_request = new MessageKey("bad_request",1);
    public static final MessageKey constraint_violation = new MessageKey("constraint_violation",1);
    public static final MessageKey client_does_not_exist = new MessageKey("client_does_not_exist",0);
    public static final MessageKey expired_token = new MessageKey("expired_token",1);

}
