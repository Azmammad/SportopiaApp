package com.matrix.sportopia.models.dto.response;

import com.matrix.sportopia.enums.PaymentMethod;
import com.matrix.sportopia.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentResponseDto {
    private Long id;
    private String userBankAccount;
    private String companyBankAccount;
    private Integer price;
    private Long reservationId;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
}
