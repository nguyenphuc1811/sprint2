package com.example.be.controller.booking_controller;

import com.example.be.model.tours.Booking;
import com.example.be.service.IBookingService;
import com.example.be.service.IToursService;
import com.example.be.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(toursService.findAllByUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable int id) {
        if (bookingService.removeBooking(id)) {
            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        }
        return new ResponseEntity<>("Xóa không thành công", HttpStatus.NOT_FOUND);
    }

    @PostMapping("add")
    public ResponseEntity<?> addBookingToCart(@RequestBody Booking booking) {
        if (bookingService.addBooking(booking)) {
            return new ResponseEntity<>("Đã Thêm thành công vào giỏ", HttpStatus.OK);
        }
        return new ResponseEntity<>("Đã có tour trong giỏ hàng", HttpStatus.BAD_REQUEST);
    }
}
