package ru.netology.domain;

public class NegativeIdException extends RuntimeException{

    public NegativeIdException(String s) {
        super(s);
    }
}
