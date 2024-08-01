package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.Payment;
import com.matrix.sportopia.entities.Reservation;
import com.matrix.sportopia.exceptions.handle.ResourceNotFoundException;
import com.matrix.sportopia.mapper.PaymentMapper;
import com.matrix.sportopia.models.dto.request.PaymentRequestDto;
import com.matrix.sportopia.models.dto.response.PaymentResponseDto;
import com.matrix.sportopia.repositories.PaymentRepository;
import com.matrix.sportopia.repositories.ReservationRepository;
import com.matrix.sportopia.services.PaymentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ReservationRepository reservationRepository;

    @Value("${company.bank.account}")
    private String companyBankAccount;
    @Override
    public PaymentResponseDto getById(Long id) {
        log.info("-> payment-service get by id operation with id = {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Payment not found with id " + id));
        PaymentResponseDto responseDto = paymentMapper.toResponse(payment);
        log.info("-> successful! payment-service get by id operation with id = {}", id);
        return responseDto;
    }

    @Override
    public PaymentResponseDto add(PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentMapper.toEntity(paymentRequestDto);
        Reservation reservation = reservationRepository.findById(paymentRequestDto.getReservationId())
                .orElseThrow(()-> new ResourceNotFoundException("Reservation not found with id " + paymentRequestDto.getReservationId()));
        payment.setReservation(reservation);
        payment.setCompanyBankAccount(companyBankAccount);

        Payment savedPayment = paymentRepository.save(payment);

        reservation.setIsPaid(true);
        reservationRepository.save(reservation);

        log.info("-> added payment with ID {} and updated reservation status", savedPayment);
        return paymentMapper.toResponse(savedPayment);

    }

    //GETBYUSERID
    //GETBYSTADIUMID
    //GETBYSTADIUMNAME
}
