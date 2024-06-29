package com.matrix.sportopia.repositories;

import com.matrix.sportopia.entities.User;
import liquibase.license.LicenseService;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.status = 'ACTIVE'")
    List<User> findAllActiveUsers();

    @Query("select u from User u where u.status <> 'ACTIVE'")
    List<User> findAllNoActiveUsers();

    @EntityGraph(attributePaths = {"authorities"})
    Optional<User> findByUsername (String username);
}
