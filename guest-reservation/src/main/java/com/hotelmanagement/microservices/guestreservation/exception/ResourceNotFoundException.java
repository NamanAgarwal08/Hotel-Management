package com.hotelmanagement.microservices.guestreservation.exception;

public class ResourceNotFoundException extends NullPointerException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
