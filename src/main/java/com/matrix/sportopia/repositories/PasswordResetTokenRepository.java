package com.matrix.sportopia.repositories;

import com.matrix.sportopia.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    Optional<PasswordResetToken> findByUserId(Long id);

    List<PasswordResetToken> findByExpiryDateBefore(Date date);

    void deleteByUserId(Long id);
}
