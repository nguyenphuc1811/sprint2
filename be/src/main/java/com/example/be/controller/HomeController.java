package com.example.be.controller;

import com.example.be.service.IToursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class HomeController {
    @Autowired
    private IToursService toursService;

    @GetMapping("tours")
    public ResponseEntity<?> getTours(@PageableDefault(size = 6) Pageable pageable,
                                      @RequestParam(name = "locationId", defaultValue = "0") int id,
                                      @RequestParam(name = "startDate", defaultValue = "") String startDate,
                                      @RequestParam(name = "slot", defaultValue = "0") int slot) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        } catch (ParseException e) {
            startDate = "1999-11-18";
        }
        if (id == 0) {
            return new ResponseEntity<>(toursService.getAll(pageable, slot, startDate), HttpStatus.OK);
        }
        return new ResponseEntity<>(toursService.getAllByLocation(pageable, slot, startDate, id), HttpStatus.OK);
    }
}
