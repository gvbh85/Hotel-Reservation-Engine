package com.hotel.entity;

import com.hotel.enums.RoomStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

}