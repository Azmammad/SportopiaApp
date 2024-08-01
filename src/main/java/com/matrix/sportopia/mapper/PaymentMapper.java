package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Payment;
import com.matrix.sportopia.models.dto.request.PaymentRequestDto;
import com.matrix.sportopia.models.dto.response.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {
    public  abstract Payment toEntity(PaymentRequestDto paymentRequestDto);
    public abstract PaymentResponseDto toResponse(Payment payment);

}
