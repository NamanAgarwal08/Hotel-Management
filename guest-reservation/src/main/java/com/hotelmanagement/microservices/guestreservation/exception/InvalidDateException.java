package com.hotelmanagement.microservices.guestreservation.exception;

public class InvalidDateException extends Exception{
    public InvalidDateException(String message){
        super(message);
    }
}
