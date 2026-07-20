package com.hotel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.hotel.entity.Hotel;
import com.hotel.repository.HotelRepository;
import com.hotel.service.impl.HotelServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void testGetHotelById() {

        Hotel hotel = new Hotel();

        hotel.setId(1L);
        hotel.setHotelName("Taj Hotel");

        when(hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));

        Hotel result = hotelService.getHotelById(1L);

        assertEquals("Taj Hotel", result.getHotelName());

    }

}