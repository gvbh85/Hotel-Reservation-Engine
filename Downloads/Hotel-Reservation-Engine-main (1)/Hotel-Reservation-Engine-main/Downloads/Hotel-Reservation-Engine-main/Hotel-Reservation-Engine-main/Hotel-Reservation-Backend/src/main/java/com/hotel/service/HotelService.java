package com.hotel.service;

import java.util.List;

import com.hotel.entity.Hotel;

public interface HotelService {

    Hotel addHotel(Hotel hotel);

    Hotel updateHotel(Long id, Hotel hotel);

    void deleteHotel(Long id);

    List<Hotel> getAllHotels();

    Hotel getHotelById(Long id);

}