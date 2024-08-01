package com.matrix.sportopia.repositories;

import com.matrix.sportopia.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByStadiumId(Long stadiumId);
}
