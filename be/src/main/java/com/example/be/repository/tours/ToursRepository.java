package com.example.be.repository.tours;

import com.example.be.dto.tours.IToursDto;
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

    @Query(value = "select t.* , l.name as location , ifnull((t.slot - sum(b.slot)),0) as remaining from `tours` t left join `bookings` b on t.id = b.tours_id left join `location` l on t.location_id = l.id where t.start_date >= :startDate group by t.id  having remaining >= :slot order by t.start_date", nativeQuery = true)
    Page<IToursDto> getAllDto(Pageable pageable, @Param("slot") int slot, @Param("startDate") String startDate);

    @Query(value = "select t.* , l.name as location , ifnull((t.slot - sum(b.slot)),0) as remaining from `tours` t left join `bookings` b on t.id = b.tours_id left join `location` l on t.location_id = l.id where t.start_date >= :startDate & l.id = :id group by t.id  having remaining >= :slot order by t.start_date", nativeQuery = true)
    Page<IToursDto> getAllByLocationDto(Pageable pageable, @Param("slot") int slot, @Param("startDate") String StartDate, @Param("id") int id);


}
