package com.example.be.service;

import com.example.be.dto.tours.ILocationDto;
import com.example.be.model.tours.Location;

import java.util.List;

public interface ILocationService {
    List<Location> getAll();
    List<ILocationDto> getLocationByQuantityTour();
}
