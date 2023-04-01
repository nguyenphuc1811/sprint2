package com.example.be.service.impl;

import com.example.be.dto.tours.IToursDto;
import com.example.be.model.tours.Tours;
import com.example.be.repository.tours.ToursRepository;
import com.example.be.service.IToursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServiceImpl implements IToursService {
    @Autowired
    public ToursRepository toursRepository;

    @Override
    public Page<IToursDto> getAllDto(Pageable pageable, int slot, String startDate) {
        return toursRepository.getAllDto(pageable, slot, startDate);
    }

    @Override
    public Page<IToursDto> getAllByLocationDto(Pageable pageable, int slot, String startDate, int id) {
        return toursRepository.getAllByLocationDto(pageable, slot, startDate, id);
    }

    @Override
    public IToursDto findByIdDto(int id) {
        return toursRepository.findByIdDto(id);
    }

    @Override
    public Tours findById(int id) {
        return toursRepository.findById(id).get();
    }

    @Override
    public List<IToursDto> findAllByUser(int idUser) {
        return toursRepository.findAllByUser(idUser);
    }

    @Override
    public IToursDto findOneById(int id) {
        return toursRepository.findOneById(id);
    }
}
