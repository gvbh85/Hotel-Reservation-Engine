package com.hotel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.entity.Reservation;
import com.hotel.entity.Room;
import com.hotel.entity.User;
import com.hotel.enums.ReservationStatus;
import com.hotel.enums.RoomStatus;
import com.hotel.repository.ReservationRepository;
import com.hotel.repository.RoomRepository;
import com.hotel.repository.UserRepository;
import com.hotel.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<Room> searchAvailableRooms(
            Long hotelId,
            LocalDate checkIn,
            LocalDate checkOut) {

        List<Room> rooms = roomRepository.findByHotelIdAndStatus(
                hotelId,
                RoomStatus.AVAILABLE);

        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {

            boolean booked = !reservationRepository
                    .findOverlappingReservations(
                            room.getId(),
                            checkIn,
                            checkOut,
                            ReservationStatus.CONFIRMED,
                            ReservationStatus.HELD)
                    .isEmpty();

            if (!booked) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    @Override
    public Reservation bookRoom(
            Long roomId,
            Long userId,
            LocalDate checkIn,
            LocalDate checkOut) {

        RLock lock = redissonClient.getLock("ROOM_LOCK_" + roomId);

        boolean locked = false;

        try {

            // Wait up to 10 seconds to acquire the lock
            // Auto-release after 30 seconds
            locked = lock.tryLock(10, 30, TimeUnit.SECONDS);

            if (!locked) {
                throw new RuntimeException(
                        "Room is currently being booked by another customer.");
            }

            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() ->
                            new RuntimeException("Room Not Found"));

            User user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new RuntimeException("User Not Found"));

            boolean booked = !reservationRepository
                    .findOverlappingReservations(
                            roomId,
                            checkIn,
                            checkOut,
                            ReservationStatus.CONFIRMED,
                            ReservationStatus.HELD)
                    .isEmpty();

            if (booked) {
                throw new RuntimeException(
                        "Room is already booked for the selected dates.");
            }

            Reservation reservation = new Reservation();

            reservation.setRoom(room);
            reservation.setUser(user);
            reservation.setCheckInDate(checkIn);
            reservation.setCheckOutDate(checkOut);
            reservation.setStatus(ReservationStatus.HELD);

            return reservationRepository.save(reservation);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            throw new RuntimeException("Booking interrupted.", e);

        } finally {

            if (locked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void cancelReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new RuntimeException("Reservation Not Found"));

        reservation.setStatus(ReservationStatus.CANCELLED);

        reservationRepository.save(reservation);
    }

}