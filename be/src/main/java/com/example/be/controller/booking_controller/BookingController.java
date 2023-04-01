package com.example.be.controller.booking_controller;

import com.example.be.dto.tours.BookingDto;
import com.example.be.model.tours.Booking;
import com.example.be.service.IBookingService;
import com.example.be.service.IToursService;
import com.example.be.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/booking")
public class BookingController {
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IToursService toursService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingCart(@PathVariable("id") int id) {
        if (iUserService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Booking> bookingList = bookingService.findAllByPaymentAndUser(id);
        List<BookingDto> bookingDtos = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingDto bookingDto = new BookingDto();
            BeanUtils.copyProperties(booking, bookingDto);
            bookingDto.setiToursDto(toursService.findOneById(bookingDto.getId()));
            bookingDtos.add(bookingDto);
        }
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable int id) {
        if (bookingService.removeBooking(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PostMapping("add")
    public ResponseEntity<?> addBookingToCart(@RequestBody Booking booking) {
        String date = String.valueOf(LocalDate.now());
        booking.setBookingDate(date);
        if (bookingService.addBooking(booking)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateQuantityBooking(@RequestBody List<Booking> bookingList) {
        try {
            bookingService.saveQuantity(bookingList);
        } catch (NullPointerException ignored) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("payment")
    public ResponseEntity<?> paymentTour(@RequestBody List<Booking> bookingList) {
        try {
            bookingService.payment(bookingList);
        } catch (NullPointerException ignored) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
