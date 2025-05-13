package com.hotelmanagement.microservices.payment.servcice;

import com.hotelmanagement.microservices.payment.dto.ReservationDetails;
import com.hotelmanagement.microservices.payment.dto.StripeResponse;

public interface StripeServiceInterface {

    public StripeResponse makePayment(ReservationDetails reservationDetails, Long id);

}
