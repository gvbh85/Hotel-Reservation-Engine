package com.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel.dto.ReservationRequest;
import com.hotel.entity.Reservation;
import com.hotel.entity.Room;
import com.hotel.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins="*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/search")

    public List<Room> searchRooms(

            @RequestParam Long hotelId,

            @RequestParam LocalDate checkIn,

            @RequestParam LocalDate checkOut){

        return reservationService.searchAvailableRooms(

                hotelId,

                checkIn,

                checkOut);

    }

    @PostMapping

    public Reservation bookRoom(
            @Valid @RequestBody ReservationRequest request){

        return reservationService.bookRoom(

                request.getRoomId(),

                request.getUserId(),

                request.getCheckInDate(),

                request.getCheckOutDate());

    }

    @GetMapping

    public List<Reservation> getReservations(){

        return reservationService.getAllReservations();

    }
    

    @DeleteMapping("/{id}")

    public String cancelReservation(

            @PathVariable Long id){

        reservationService.cancelReservation(id);

        return "Reservation Cancelled Successfully";

    }

}