package com.matrix.sportopia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authorities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    @NotNull
    private String name;
}
