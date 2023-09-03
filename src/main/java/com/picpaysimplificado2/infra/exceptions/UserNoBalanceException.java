package com.picpaysimplificado2.infra.exceptions;

public class UserNoBalanceException extends RuntimeException {
    public UserNoBalanceException(String message) {
        super(message);
    }
}
