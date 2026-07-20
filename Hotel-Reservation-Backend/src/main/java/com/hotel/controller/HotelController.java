package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel.entity.Hotel;
import com.hotel.service.HotelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
@Tag(
    name = "Hotel APIs",
    description = "Hotel Management APIs"
)
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Add New Hotel")
    @PostMapping
    public Hotel addHotel(@RequestBody Hotel hotel) {
        return hotelService.addHotel(hotel);
    }

    @Operation(summary = "Update Hotel")
    @PutMapping("/{id}")
    public Hotel updateHotel(
            @PathVariable Long id,
            @RequestBody Hotel hotel) {

        return hotelService.updateHotel(id, hotel);
    }

    @Operation(summary = "Delete Hotel")
    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Long id) {

        hotelService.deleteHotel(id);

        return "Hotel Deleted Successfully";
    }

    @Operation(summary = "Get All Hotels")
    @GetMapping
    public List<Hotel> getAllHotels() {

        return hotelService.getAllHotels();
    }

    @Operation(summary = "Get Hotel By ID")
    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id) {

        return hotelService.getHotelById(id);
    }

}