package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel.entity.Room;
import com.hotel.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public Room addRoom(
            @RequestBody Room room,
            @RequestParam Long hotelId,
            @RequestParam Long roomTypeId) {

        return roomService.addRoom(room, hotelId, roomTypeId);
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }
    

    @PutMapping("/{id}")
    public Room updateRoom(
            @PathVariable Long id,
            @RequestBody Room room) {

        return roomService.updateRoom(id, room);
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Long id) {

        roomService.deleteRoom(id);

        return "Room Deleted Successfully";
    }
}