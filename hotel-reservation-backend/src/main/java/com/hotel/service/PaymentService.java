package com.hotel.service;

import com.hotel.dto.PaymentRequest;
import com.hotel.entity.Payment;

public interface PaymentService {

    Payment processPayment(PaymentRequest request);

}