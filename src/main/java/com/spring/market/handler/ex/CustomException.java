package com.spring.market.handler.ex;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
