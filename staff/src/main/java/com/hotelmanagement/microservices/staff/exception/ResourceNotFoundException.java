package com.hotelmanagement.microservices.staff.exception;

public class ResourceNotFoundException extends NullPointerException{

    public ResourceNotFoundException(String message){
        super(message);
    }

}
