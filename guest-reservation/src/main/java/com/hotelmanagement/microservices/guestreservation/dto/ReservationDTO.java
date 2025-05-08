package com.hotelmanagement.microservices.guestreservation.dto;

import com.hotelmanagement.microservices.guestreservation.exception.InvalidDateException;
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
public class ReservationDTO {

    private Long Id;

    @NotNull(message = "Guest id is required field!")
    private Long guestId;

    @NotNull(message = "List of room numbers to be booked can't be null!")
    private List<Integer> roomNumbers;

    @NotNull(message = "CheckIn date can't be null!")
    private String checkInDate;

    @NotNull(message = "CheckOut date can't be null!")
    private String checkOutDate;

    @NotNull(message = "Specify number of adults!")
    private Integer numberOfAdults;

    @NotNull(message = "Specify number of children!")
    private Integer numberOfChildren;

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
        }catch(ParseException e){
            throw new InvalidDateException("CheckOutDate invalid or in incorrect format(yyyy-MM-dd)!");
        }catch (InvalidDateException e){
            throw new InvalidDateException(e.getMessage());
        }
    }

}