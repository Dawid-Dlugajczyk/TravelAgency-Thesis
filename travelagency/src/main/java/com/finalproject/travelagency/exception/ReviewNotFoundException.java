package com.finalproject.travelagency.exception;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(String message){
        super(message);
    }
}
