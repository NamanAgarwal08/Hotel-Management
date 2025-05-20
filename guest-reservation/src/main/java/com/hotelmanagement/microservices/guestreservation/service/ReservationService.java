package com.hotelmanagement.microservices.guestreservation.service;

import com.hotelmanagement.microservices.guestreservation.config.PaymentServiceProxy;
import com.hotelmanagement.microservices.guestreservation.config.RoomServiceProxy;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDetailsPayment;
import com.hotelmanagement.microservices.guestreservation.dto.RoomDTO;
import com.hotelmanagement.microservices.guestreservation.dto.StripeResponse;
import com.hotelmanagement.microservices.guestreservation.entity.ReservationEntity;
import com.hotelmanagement.microservices.guestreservation.exception.FeignServiceUnavailableException;
import com.hotelmanagement.microservices.guestreservation.exception.ResourceNotFoundException;
import com.hotelmanagement.microservices.guestreservation.exception.RoomNotAvailableException;
import com.hotelmanagement.microservices.guestreservation.repository.ReservationRepository;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService implements ReservationServiceInterface{

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RoomServiceProxy roomServiceProxy;

    @Autowired
    PaymentServiceProxy paymentServiceProxy;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public StripeResponse createReservation(ReservationDTO reservation) throws RoomNotAvailableException, FeignServiceUnavailableException {
        List<RoomDTO> availableRooms;
        try{
            availableRooms = checkAvailability(reservation.getCheckInDate(), reservation.getCheckOutDate());
        }catch(FeignServiceUnavailableException e){
            throw new FeignServiceUnavailableException("Room Service is temporarily down!");
        }


        Map<Integer, Long> availableRoomNumbers = availableRooms.stream().collect(Collectors.toMap(RoomDTO::getRoomNumber, RoomDTO::getAmount));

        Long amount = 0L;

        for(int i=0;i<reservation.getRoomNumbers().size();i++){
            if(!availableRoomNumbers.containsKey(reservation.getRoomNumbers().get(i))){
                throw new RoomNotAvailableException("Specified room(s) not available for booking!");
            }else{
                amount+=availableRoomNumbers.get(reservation.getRoomNumbers().get(i));
            }
        }

        StripeResponse stripeResponse;

        try{
            ReservationDetailsPayment reservationDetailsPayment = new ReservationDetailsPayment(amount, "INR", reservation.getRoomNumbers(), reservation.getCheckInDate(), reservation.getCheckOutDate());
            stripeResponse = paymentServiceProxy.makePayment(reservationDetailsPayment).getBody();
        }catch (Exception e){
            throw new FeignServiceUnavailableException("Payment Service is temporarily down!");
        }

        ReservationEntity reservationEntity = modelMapper.map(reservation, ReservationEntity.class);
        reservationEntity.setStatus("PENDING");
        reservationEntity.setSessionId(stripeResponse.getSessionId());
        reservationRepository.save(reservationEntity);

        return stripeResponse;
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(reservation -> modelMapper.map(reservation, ReservationDTO.class)).toList();
    }

    @Override
    public ReservationDTO getReservations(Long id) {
        ReservationEntity foundReservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No reservation found with the provided id: " + id));
        return modelMapper.map(foundReservation, ReservationDTO.class);
    }

    @Override
    public ReservationDTO updateReservationDetails(Long id, ReservationDTO reservationDTO) {
        ReservationEntity foundReservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No reservation found with the provided id: " + id));
        modelMapper.map(reservationDTO,foundReservation);

        reservationRepository.save(foundReservation);

        return modelMapper.map(foundReservation, ReservationDTO.class);
    }

    @Override
    public String deleteReservation(Long id) {
        ReservationEntity foundReservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No reservation found with the provided id: " + id));

        reservationRepository.delete(foundReservation);

        return "Reservation details deleted successfully!";
    }

    @Override
    public List<RoomDTO> checkAvailability(String checkInDate, String checkOutDate) throws FeignServiceUnavailableException {
        List<RoomDTO> allRooms;
        try{
            allRooms = roomServiceProxy.getAllRooms().getBody().getData();
        }catch(Exception e){
            throw new FeignServiceUnavailableException("Room Service is temporarily down!");
        }

        Set<Integer> allRoomNumbers = allRooms.stream().map(room -> room.getRoomNumber()).collect(Collectors.toSet());

        List<ReservationEntity> overlapped = reservationRepository.checkAvailability(checkInDate, checkOutDate);

        for(ReservationEntity re : overlapped){
            List<Integer> rooms = re.getRoomNumbers();
            for(Integer roomNumber : rooms){
                if(allRoomNumbers.contains(roomNumber)){
                    allRoomNumbers.remove(roomNumber);
                }
            }
        }

        return allRooms.stream().filter(room -> allRoomNumbers.contains(room.getRoomNumber())).toList();
    }

}