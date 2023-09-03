package com.picpaysimplificado2.infra.exceptions;

public class OperationUnauthorizedException extends RuntimeException {
    public OperationUnauthorizedException(String message) {
        super(message);
    }
}
