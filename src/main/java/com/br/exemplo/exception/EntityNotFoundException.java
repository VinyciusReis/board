package com.br.exemplo.exception;

public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String mesage) {
        super(mesage);
    }
}