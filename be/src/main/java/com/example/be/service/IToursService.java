package com.example.be.service;

import com.example.be.dto.tours.IToursDto;
import com.example.be.model.tours.Tours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IToursService {
    Page<Tours> getAll(Pageable pageable, int slot, String startDate);

    Page<Tours> getAllByLocation(Pageable pageable, int slot, String startDate, int id);

    Page<IToursDto> getAllDto(Pageable pageable, int slot, String startDate);

    Page<IToursDto> getAllByLocationDto(Pageable pageable, int slot, String startDate, int id);

    IToursDto findByIdDto(int id);

    Tours findById(int id);

    List<IToursDto> findAllByUser(int idUser);
}
