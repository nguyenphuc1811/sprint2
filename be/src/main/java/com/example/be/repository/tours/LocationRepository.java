package com.example.be.repository.tours;

import com.example.be.model.tours.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
