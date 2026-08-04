package com.hotel.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dto.PaymentRequest;
import com.hotel.entity.Payment;
import com.hotel.entity.Reservation;
import com.hotel.enums.PaymentStatus;
import com.hotel.enums.ReservationStatus;
import com.hotel.repository.PaymentRepository;
import com.hotel.repository.ReservationRepository;
import com.hotel.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Payment processPayment(PaymentRequest request) {

        Reservation reservation =
                reservationRepository.findById(request.getReservationId())
                .orElseThrow(() ->
                        new RuntimeException("Reservation Not Found"));

        // Move reservation to payment processing
        reservation.setStatus(
                ReservationStatus.PAYMENT_PROCESSING);

        reservationRepository.save(reservation);

        // Mock payment gateway
        boolean paymentSuccess = request.getAmount() > 0;

        Payment payment = new Payment();

        payment.setAmount(request.getAmount());

        payment.setTransactionId(
                UUID.randomUUID().toString());

        payment.setReservation(reservation);

        if (paymentSuccess) {

            payment.setPaymentStatus(PaymentStatus.SUCCESS);

            reservation.setStatus(
                    ReservationStatus.CONFIRMED);

        } else {

            payment.setPaymentStatus(PaymentStatus.FAILED);

            reservation.setStatus(
                    ReservationStatus.RELEASED);

        }

        reservationRepository.save(reservation);

        return paymentRepository.save(payment);

    }

}