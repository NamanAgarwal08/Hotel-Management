package com.hotelmanagement.microservices.guestreservation.exception;

public class FeignServiceNotAvailableException extends Exception{
    public FeignServiceNotAvailableException(String message){
        super(message);
    }
}
