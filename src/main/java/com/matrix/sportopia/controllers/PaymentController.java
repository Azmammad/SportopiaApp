package com.matrix.sportopia.controllers;

import com.matrix.sportopia.services.PaymentService;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@Data
public class PaymentController {
    private final PaymentService paymentService;


}
