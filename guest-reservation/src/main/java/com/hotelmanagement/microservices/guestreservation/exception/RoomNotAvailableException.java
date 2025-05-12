package com.hotelmanagement.microservices.guestreservation.exception;

public class RoomNotAvailableException extends Exception{
    public RoomNotAvailableException(String message){
        super(message);
    }
}
