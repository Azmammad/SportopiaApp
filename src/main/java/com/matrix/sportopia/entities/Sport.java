package com.matrix.sportopia.entities;

import com.matrix.sportopia.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "sports")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @NotNull
    private Boolean status;



    @OneToMany(mappedBy = "sport",cascade = CascadeType.ALL)
    private List<Stadium> stadions;
}
