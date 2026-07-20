package com.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.entity.Hotel;
import com.hotel.repository.HotelRepository;
import com.hotel.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel addHotel(Hotel hotel) {

        return hotelRepository.save(hotel);

    }

    @Override
    public Hotel updateHotel(Long id, Hotel hotel) {

        Hotel existing =
                hotelRepository.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Hotel not found"));

        existing.setHotelName(hotel.getHotelName());
        existing.setCity(hotel.getCity());
        existing.setState(hotel.getState());
        existing.setAddress(hotel.getAddress());
        existing.setDescription(hotel.getDescription());

        return hotelRepository.save(existing);

    }

    @Override
    public void deleteHotel(Long id) {

        hotelRepository.deleteById(id);

    }

    @Override
    public List<Hotel> getAllHotels() {

        return hotelRepository.findAll();

    }

    @Override
    public Hotel getHotelById(Long id) {

        return hotelRepository.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Hotel not found"));

    }

}