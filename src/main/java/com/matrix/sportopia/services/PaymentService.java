package com.matrix.sportopia.services;

import com.matrix.sportopia.models.dto.request.PaymentRequestDto;
import com.matrix.sportopia.models.dto.response.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto getById(Long id);
    PaymentResponseDto add(PaymentRequestDto paymentRequestDto);
}
