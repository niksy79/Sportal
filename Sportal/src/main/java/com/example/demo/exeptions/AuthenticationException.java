package com.example.demo.exeptions;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String msg) {
        super(msg);
    }
}
