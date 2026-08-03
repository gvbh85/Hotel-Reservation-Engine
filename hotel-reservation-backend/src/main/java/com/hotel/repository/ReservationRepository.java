package com.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.entity.Reservation;
import com.hotel.entity.User;
import com.hotel.enums.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

    @Query("SELECT r FROM Reservation r " +
           "WHERE r.room.id = :roomId " +
           "AND r.checkInDate < :checkOut " +
           "AND r.checkOutDate > :checkIn " +
           "AND r.status IN (:status1, :status2)")
    List<Reservation> findOverlappingReservations(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("status1") ReservationStatus status1,
            @Param("status2") ReservationStatus status2);
}