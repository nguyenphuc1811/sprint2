package com.example.be.repository;

import com.example.be.model.tours.Tours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ToursRepository extends JpaRepository<Tours, Integer> {
    @Query(value = "select * from tours where slot >= :slot && start_date >= :startDate order by start_date", nativeQuery = true)
    Page<Tours> getAll(Pageable pageable, @Param("slot") int slot, @Param("startDate") String startDate);

    @Query(value = "select * from tours where slot >= :slot && start_date >= :startDate && location_id = :id order by start_date", nativeQuery = true)
    Page<Tours> getAllByLocation(Pageable pageable, @Param("slot") int slot, @Param("startDate") String StartDate, @Param("id") int id);
}
