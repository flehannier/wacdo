package com.wacdo.controllers.exception;

public class TechnicalException extends Exception{
    public TechnicalException(String message, Throwable err){
        super(message, err);
    }
    public TechnicalException(String message){
        super(message);
    }
}
