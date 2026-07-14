package com.hotel.entity;

import java.time.LocalDate;

import com.hotel.enums.ReservationStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}