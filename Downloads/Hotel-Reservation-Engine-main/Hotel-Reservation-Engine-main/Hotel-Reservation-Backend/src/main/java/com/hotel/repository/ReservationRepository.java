package com.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.entity.Reservation;
import com.hotel.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{

    List<Reservation> findByUser(User user);

}