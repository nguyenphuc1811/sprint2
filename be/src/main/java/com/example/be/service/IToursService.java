package com.example.be.service;

import com.example.be.dto.tours.IToursDto;
import com.example.be.model.tours.Tours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IToursService {

    Page<IToursDto> getAllDto(Pageable pageable, int slot, String startDate);

    Page<IToursDto> getAllByLocationDto(Pageable pageable, int slot, String startDate, int id);

    IToursDto findByIdDto(int id);

    Tours findById(int id);

    List<IToursDto> findAllByUser(int idUser);

    IToursDto findOneById(int id);
}
