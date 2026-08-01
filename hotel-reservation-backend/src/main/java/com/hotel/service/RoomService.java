package com.hotel.service;

import java.util.List;

import com.hotel.entity.Room;

public interface RoomService {

    Room addRoom(Room room, Long hotelId, Long roomTypeId);

    Room updateRoom(Long id, Room room);

    void deleteRoom(Long id);

    List<Room> getAllRooms();

    Room getRoomById(Long id);

}