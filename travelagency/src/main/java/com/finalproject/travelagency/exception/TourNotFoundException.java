package com.finalproject.travelagency.exception;

public class TourNotFoundException extends RuntimeException{
    public TourNotFoundException(String message){
        super(message);
    }
}
