package com.matrix.sportopia.repositories;

import com.matrix.sportopia.entities.Sport;
import com.matrix.sportopia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportRepository extends JpaRepository<Sport,Long> {

    @Query("select s from Sport s where s.status = 1")
    List<Sport> findAllActiveSports();

}
