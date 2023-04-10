package com.example.be.repository.tours;

import com.example.be.dto.tours.ILocationDto;
import com.example.be.model.tours.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query(value = "select l.id,l.name,l.img_location as imgLocation , ifnull(count(l.id),0) as mostLocation from `location` l left join tours t on l.id = t.location_id group by l.id order by mostLocation desc ", nativeQuery = true)
    List<ILocationDto> getLocationByQuantityTour();
}
