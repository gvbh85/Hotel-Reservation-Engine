package com.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.entity.Hotel;
import com.hotel.entity.Room;
import com.hotel.entity.RoomType;
import com.hotel.enums.RoomStatus;
import com.hotel.repository.HotelRepository;
import com.hotel.repository.RoomRepository;
import com.hotel.repository.RoomTypeRepository;
import com.hotel.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public Room addRoom(Room room, Long hotelId, Long roomTypeId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel Not Found"));

        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RuntimeException("Room Type Not Found"));

        room.setHotel(hotel);
        room.setRoomType(roomType);
        room.setStatus(RoomStatus.AVAILABLE);

        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id, Room room) {

        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Not Found"));

        existing.setRoomNumber(room.getRoomNumber());
        existing.setStatus(room.getStatus());

        return roomRepository.save(existing);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Not Found"));
    }
}