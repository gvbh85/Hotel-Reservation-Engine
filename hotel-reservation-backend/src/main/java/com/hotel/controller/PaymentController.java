package com.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hotel.dto.PaymentRequest;
import com.hotel.entity.Payment;
import com.hotel.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    
    private PaymentService paymentService;

    @PostMapping
    public Payment makePayment(
            @RequestBody PaymentRequest request) {

        return paymentService.processPayment(request);

    }

}