package com.matrix.sportopia.models.dto.request;

import com.matrix.sportopia.enums.PaymentMethod;
import com.matrix.sportopia.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private String userBankAccount;
    private Integer price;
    private Long reservationId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String companyBankAccount; // Default company bank account
}
