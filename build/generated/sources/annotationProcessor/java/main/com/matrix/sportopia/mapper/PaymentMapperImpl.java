package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.Payment;
import com.matrix.sportopia.models.dto.request.PaymentRequestDto;
import com.matrix.sportopia.models.dto.response.PaymentResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-01T15:00:29+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl extends PaymentMapper {

    @Override
    public Payment toEntity(PaymentRequestDto paymentRequestDto) {
        if ( paymentRequestDto == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setUserBankAccount( paymentRequestDto.getUserBankAccount() );
        payment.setCompanyBankAccount( paymentRequestDto.getCompanyBankAccount() );
        payment.setPrice( paymentRequestDto.getPrice() );
        payment.setPaymentStatus( paymentRequestDto.getPaymentStatus() );
        payment.setPaymentMethod( paymentRequestDto.getPaymentMethod() );

        return payment;
    }

    @Override
    public PaymentResponseDto toResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();

        paymentResponseDto.setId( payment.getId() );
        paymentResponseDto.setUserBankAccount( payment.getUserBankAccount() );
        paymentResponseDto.setCompanyBankAccount( payment.getCompanyBankAccount() );
        paymentResponseDto.setPrice( payment.getPrice() );
        paymentResponseDto.setPaymentStatus( payment.getPaymentStatus() );
        paymentResponseDto.setPaymentMethod( payment.getPaymentMethod() );

        return paymentResponseDto;
    }
}
