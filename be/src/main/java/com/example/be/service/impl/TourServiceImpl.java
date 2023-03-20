package com.example.be.service.impl;

import com.example.be.model.tours.Tours;
import com.example.be.repository.tours.ToursRepository;
import com.example.be.service.IToursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TourServiceImpl implements IToursService {
    @Autowired
    public ToursRepository toursRepository;

    public Page<Tours> getAll(Pageable pageable, int slot, String startDate) {
        return toursRepository.getAll(pageable, slot, startDate);
    }

    public Page<Tours> getAllByLocation(Pageable pageable, int slot, String startDate, int id) {
        return toursRepository.getAllByLocation(pageable, slot, startDate, id);
    }
}
