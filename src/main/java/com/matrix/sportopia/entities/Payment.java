package com.matrix.sportopia.entities;

import com.matrix.sportopia.enums.PaymentMethod;
import com.matrix.sportopia.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userBankAccount;

    @Column(nullable = false)
    private String companyBankAccount;

    @Column(nullable = false)
    private Integer price;

    @OneToOne
    @JoinColumn(name = "reservation_id",referencedColumnName = "id",nullable = false)
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;


}
