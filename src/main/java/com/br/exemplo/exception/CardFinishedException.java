package com.br.exemplo.exception;

public class CardFinishedException extends RuntimeException {

    public CardFinishedException(String mesage) {
        super(mesage);
    }
}