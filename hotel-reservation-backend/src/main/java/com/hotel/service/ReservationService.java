package com.hotel.service;

import java.time.LocalDate;
import java.util.List;

import com.hotel.entity.Reservation;
import com.hotel.entity.Room;

public interface ReservationService {

    List<Room> searchAvailableRooms(
            Long hotelId,
            LocalDate checkIn,
            LocalDate checkOut);

    Reservation bookRoom(
            Long roomId,
            Long userId,
            LocalDate checkIn,
            LocalDate checkOut);

    List<Reservation> getAllReservations();

    void cancelReservation(Long reservationId);

}