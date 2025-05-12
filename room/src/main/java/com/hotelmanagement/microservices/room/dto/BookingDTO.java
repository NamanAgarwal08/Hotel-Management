package com.hotelmanagement.microservices.room.dto;

import com.hotelmanagement.microservices.room.exception.InvalidDateException;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    @NotNull(message = "Specify the list of room numbers to be booked!")
    private List<Integer> roomNumbers;

    @NotNull(message = "CheckIn date is required!")
    private String checkInDate;

    @NotNull(message = "CheckOut date is required!")
    private String checkOutDate;

    public void setCheckInDate(String checkInDate) throws InvalidDateException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date d1 = sdf.parse(checkInDate);
            this.checkInDate = checkInDate;
        }catch(ParseException e){
            throw new InvalidDateException("CheckInDate invalid or in incorrect format(yyyy-MM-dd)!");
        }
    }

    public void setCheckOutDate(String checkOutDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date d1 = sdf.parse(this.getCheckInDate());
            Date d2 = sdf.parse(checkOutDate);

            if(d2.before(d1)){
                throw new InvalidDateException("CheckOutDate should be after CheckInDate!");
            }
            this.checkOutDate = checkOutDate;
        }catch(ParseException e){
            throw new InvalidDateException("CheckOutDate invalid or in incorrect format(yyyy-MM-dd)!");
        }catch (InvalidDateException e){
            throw new InvalidDateException(e.getMessage());
        }
    }

}