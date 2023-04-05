package com.example.be.controller.booking_controller;

import com.example.be.dto.tours.BookingDto;
import com.example.be.dto.tours.IToursDto;
import com.example.be.model.tours.Booking;
import com.example.be.model.tours.Tours;
import com.example.be.service.IBookingService;
import com.example.be.service.IToursService;
import com.example.be.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        if (iUserService.findById(id) == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        List<Booking> bookingList = bookingService.findAllByPaymentAndUser(id, false);
        List<BookingDto> bookingDtos = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingDto bookingDto = new BookingDto();
            BeanUtils.copyProperties(booking, bookingDto);
            bookingDto.setiToursDto(toursService.findOneById(booking.getTours().getId()));
            bookingDtos.add(bookingDto);
        }
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }

    //    @GetMapping("detail/{id}")
//    public ResponseEntity<?> getBookingCartPayment(@PathVariable("id") int id) {
//        if (iUserService.findById(id) == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        Map<String, List<Booking>> stringListMap = new HashMap<>();
//        List<Booking> list = bookingService.findAllByPaymentAndUser(id, true);
//        List<Booking> list1;
//        for (Booking booking : list) {
//            if (stringListMap.containsKey(booking.getBookingDate())) {
//                list1 = stringListMap.get(booking.getBookingDate());
//                list1.add(booking);
//                stringListMap.replace(booking.getBookingDate(), list1);
//                continue;
//            }
//            List<Booking> bookingList = new ArrayList<>();
//            bookingList.add(booking);
//            stringListMap.put(booking.getBookingDate(), bookingList);
//        }
//        return new ResponseEntity<>(stringListMap, HttpStatus.OK);
//    }
    @GetMapping("detail/{id}")
    public ResponseEntity<?> getBookingCartPayment(@PathVariable("id") int id,
                                                   @PageableDefault(size = 5) Pageable pageable) {
        if (iUserService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookingService.getDetailPayment(id, pageable), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable int id) {
        if (bookingService.removeBooking(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addBookingToCart(@RequestBody Booking booking) {
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

    @PostMapping("payment")
    public ResponseEntity<?> checkPaymentTour(@RequestBody List<Booking> bookingList) {
        List<IToursDto> tours = bookingService.checkRemaining(bookingList);
        if (tours.size() == 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(tours, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("payment")
    public ResponseEntity<?> paymentTour(@RequestBody List<Booking> bookingList) {
        try {
            List<IToursDto> tours = bookingService.checkRemaining(bookingList);
            if (tours.size() == 0) {
                bookingService.payment(bookingList);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(tours, HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
