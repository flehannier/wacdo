package com.wacdo.controllers.exception;

public class FunctionalException extends Exception{
    public FunctionalException(String message, Throwable err){
        super(message, err);
    }
    public FunctionalException(String message){
        super(message);
    }
}
