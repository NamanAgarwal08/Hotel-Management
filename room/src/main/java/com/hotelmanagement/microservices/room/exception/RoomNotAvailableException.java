package com.hotelmanagement.microservices.room.exception;

public class RoomNotAvailableException extends Exception{
    public RoomNotAvailableException(String message){
        super(message);
    }
}
