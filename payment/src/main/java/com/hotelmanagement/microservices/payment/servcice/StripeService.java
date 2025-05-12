package com.hotelmanagement.microservices.payment.servcice;

import com.hotelmanagement.microservices.payment.dto.ReservationDetails;
import com.hotelmanagement.microservices.payment.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService implements StripeServiceInterface {

    @Value("${stripe.key}")
    private String stripeApiKey;

    @Override
    public StripeResponse makePayment(ReservationDetails reservationDetails){
        Stripe.apiKey = stripeApiKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(reservationDetails.getName())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(reservationDetails.getCurrency()==null ? "INR" : reservationDetails.getCurrency())
                        .setUnitAmount(reservationDetails.getAmount())
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(reservationDetails.getQuantity())
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8084/payment/success?sessionId={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8084/payment/cancel?sessionId={CHECKOUT_SESSION_ID}")
                .addLineItem(lineItem)
                .build();

        Session session = null;

        try{
            session = Session.create(params);
        }catch (StripeException e) {
            throw new RuntimeException(e);
        }

        StripeResponse sessionResponse = StripeResponse.builder()
                .status("SUCCESS")
                .message("PAYMENT session created!")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();

        return sessionResponse;
    }

}
