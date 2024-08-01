package com.matrix.sportopia.repositories;

import com.matrix.sportopia.entities.Stadium;
import com.matrix.sportopia.enums.StadiumStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium,Long> {
    List<Stadium> findBySportId(Long sportId);
    List<Stadium> findAllByStatusNot(StadiumStatus status);
    List<Stadium> findBySportIdAndStatusNot(Long sportId, StadiumStatus status);

    @Query("select s from Stadium s where lower(s.name) like lower(concat(:name, '%'))")
    List<Stadium> findStadiumByName(@Param("name") String name);

}
