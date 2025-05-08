package com.hotelmanagement.microservices.room.exception;

public class InvalidDateException extends Exception{
    public InvalidDateException(String message){
        super(message);
    }
}
