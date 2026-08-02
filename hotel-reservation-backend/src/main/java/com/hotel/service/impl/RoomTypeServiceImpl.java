package com.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.entity.RoomType;
import com.hotel.repository.RoomTypeRepository;
import com.hotel.service.RoomTypeService;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public RoomType addRoomType(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    @Override
    public RoomType updateRoomType(Long id, RoomType roomType) {

        RoomType existing = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Type Not Found"));

        existing.setTypeName(roomType.getTypeName());
        existing.setPricePerNight(roomType.getPricePerNight());
        existing.setMaxGuests(roomType.getMaxGuests());

        return roomTypeRepository.save(existing);
    }

    @Override
    public void deleteRoomType(Long id) {
        roomTypeRepository.deleteById(id);
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room Type Not Found"));
    }
}