package com.matrix.sportopia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.matrix.sportopia.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    @NotNull
    private String username;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private String password;

    @Column(nullable = false)
    @Size(min = 16, max = 16)
    private String bankAccount;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "photo_path")
    private String photoPath;



    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Reservation> reservations;


    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<Authority> authorities = new HashSet<>();

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
}
