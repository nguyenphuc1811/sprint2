package com.example.be.controller;

import com.example.be.service.ILocationService;
import com.example.be.service.IToursService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ILocationService locationService;

    @GetMapping("toursDto")
    public ResponseEntity<?> getToursDto(@PageableDefault(size = 6) Pageable pageable,
                                         @RequestParam(name = "locationId", defaultValue = "0") int id,
                                         @RequestParam(name = "startDate", defaultValue = "") String startDate,
                                         @RequestParam(name = "slot", defaultValue = "0") int slot) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        } catch (ParseException e) {
            startDate = "1999-11-18";
        }
        if (id == 0) {
            return new ResponseEntity<>(toursService.getAllDto(pageable, slot, startDate), HttpStatus.OK);
        }
        return new ResponseEntity<>(toursService.getAllByLocationDto(pageable, slot, startDate, id), HttpStatus.OK);
    }

    @GetMapping("location")
    public ResponseEntity<?> getLocation() {
        return new ResponseEntity<>(locationService.getLocationByQuantityTour(), HttpStatus.OK);
    }

    @GetMapping("detail")
    public ResponseEntity<?> findByIdTour(@RequestParam(name = "id") int id) {
        if (toursService.findByIdDto(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(toursService.findByIdDto(id), HttpStatus.OK);
    }
}
