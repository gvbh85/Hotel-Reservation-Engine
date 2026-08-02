package com.hotel.service;

import java.util.List;

import com.hotel.entity.RoomType;

public interface RoomTypeService {

    RoomType addRoomType(RoomType roomType);

    RoomType updateRoomType(Long id, RoomType roomType);

    void deleteRoomType(Long id);

    List<RoomType> getAllRoomTypes();

    RoomType getRoomTypeById(Long id);

}