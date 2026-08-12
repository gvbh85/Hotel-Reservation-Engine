package com.hotel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import com.hotel.entity.Reservation;
import com.hotel.entity.Room;
import com.hotel.entity.User;
import com.hotel.enums.ReservationStatus;
import com.hotel.repository.ReservationRepository;
import com.hotel.repository.RoomRepository;
import com.hotel.repository.UserRepository;
import com.hotel.service.impl.ReservationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RedissonClient redissonClient;

    @Mock
    private RLock lock;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void testBookRoomAlreadyBooked() throws Exception {

        Room room = new Room();
        room.setId(1L);

        User user = new User();
        user.setId(1L);

        when(redissonClient.getLock(anyString())).thenReturn(lock);

        when(lock.tryLock(anyLong(), anyLong(), any(TimeUnit.class)))
                .thenReturn(true);

        when(lock.isHeldByCurrentThread()).thenReturn(true);

        when(roomRepository.findById(1L))
                .thenReturn(Optional.of(room));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Reservation existingReservation = new Reservation();

        when(reservationRepository.findOverlappingReservations(
                anyLong(),
                any(LocalDate.class),
                any(LocalDate.class),
                eq(ReservationStatus.CONFIRMED),
                eq(ReservationStatus.HELD)))
                .thenReturn(List.of(existingReservation));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                reservationService.bookRoom(
                        1L,
                        1L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 5)));

        assertEquals(
                "Room is already booked for the selected dates.",
                exception.getMessage());

        verify(lock).unlock();
    }

    @Test
    void testBookRoomSuccessfully() throws Exception {

        Room room = new Room();
        room.setId(1L);

        User user = new User();
        user.setId(1L);

        when(redissonClient.getLock(anyString())).thenReturn(lock);

        when(lock.tryLock(anyLong(), anyLong(), any(TimeUnit.class)))
                .thenReturn(true);

        when(lock.isHeldByCurrentThread()).thenReturn(true);

        when(roomRepository.findById(1L))
                .thenReturn(Optional.of(room));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(reservationRepository.findOverlappingReservations(
                anyLong(),
                any(LocalDate.class),
                any(LocalDate.class),
                eq(ReservationStatus.CONFIRMED),
                eq(ReservationStatus.HELD)))
                .thenReturn(List.of());

        Reservation savedReservation = new Reservation();
        savedReservation.setId(1L);
        savedReservation.setRoom(room);
        savedReservation.setUser(user);
        savedReservation.setStatus(ReservationStatus.HELD);

        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(savedReservation);

        Reservation result = reservationService.bookRoom(
                1L,
                1L,
                LocalDate.of(2026, 8, 1),
                LocalDate.of(2026, 8, 5));

        assertNotNull(result);
        assertEquals(ReservationStatus.HELD, result.getStatus());

        verify(reservationRepository, times(1))
                .save(any(Reservation.class));

        verify(lock).unlock();
    }
}