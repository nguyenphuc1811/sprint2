package com.example.be.service.impl;

import com.example.be.dto.tours.ILocationDto;
import com.example.be.model.tours.Location;
import com.example.be.repository.tours.LocationRepository;
import com.example.be.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements ILocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAll() {
        return this.locationRepository.findAll();
    }

    public List<ILocationDto> getLocationByQuantityTour() {
        return locationRepository.getLocationByQuantityTour();
    }
}
