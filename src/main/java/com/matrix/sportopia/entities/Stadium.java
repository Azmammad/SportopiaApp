package com.matrix.sportopia.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "stadiums")
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false,unique = true)
    private String address;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "sport_id",nullable = false)
    private Sport sport;

    @NotNull
    private Boolean status;

    @OneToMany(mappedBy = "stadium",fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
