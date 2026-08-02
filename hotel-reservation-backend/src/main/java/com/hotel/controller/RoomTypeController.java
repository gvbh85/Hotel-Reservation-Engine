package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel.entity.RoomType;
import com.hotel.service.RoomTypeService;

@RestController
@RequestMapping("/api/room-types")
@CrossOrigin(origins = "*")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping
    public RoomType addRoomType(@RequestBody RoomType roomType) {
        return roomTypeService.addRoomType(roomType);
    }

    @GetMapping
    public List<RoomType> getAllRoomTypes() {
        return roomTypeService.getAllRoomTypes();
    }

    @GetMapping("/{id}")
    public RoomType getRoomType(@PathVariable Long id) {
        return roomTypeService.getRoomTypeById(id);
    }
    

    @PutMapping("/{id}")
    public RoomType updateRoomType(
            @PathVariable Long id,
            @RequestBody RoomType roomType) {

        return roomTypeService.updateRoomType(id, roomType);
    }

    @DeleteMapping("/{id}")
    public String deleteRoomType(@PathVariable Long id) {

        roomTypeService.deleteRoomType(id);

        return "Room Type Deleted Successfully";
    }
}