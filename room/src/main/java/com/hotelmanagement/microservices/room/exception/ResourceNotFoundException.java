package com.hotelmanagement.microservices.room.exception;

public class ResourceNotFoundException extends NullPointerException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
