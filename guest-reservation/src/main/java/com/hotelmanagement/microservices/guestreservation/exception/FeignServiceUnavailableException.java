package com.hotelmanagement.microservices.guestreservation.exception;

public class FeignServiceUnavailableException extends Exception{

    public FeignServiceUnavailableException(String message){
        super(message);
    }

}
