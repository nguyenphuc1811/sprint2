package com.example.be.service.impl;

import com.example.be.model.tours.Booking;
import com.example.be.model.tours.Tours;
import com.example.be.model.user.User;
import com.example.be.repository.booking.BookingRepository;
import com.example.be.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements IBookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public boolean addBooking(Booking booking) {
        if (findByUserAndTours(booking) != null) {
            return false;
        }
        bookingRepository.save(booking);
        return true;
    }

    public List<Booking> getListCard(boolean check, User user) {
        return bookingRepository.findAllByPaymentAndUser(check, user);
    }

    public boolean removeBooking(int id) {
        if (getById(id) != null) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Booking getById(int id) {
        return bookingRepository.findById(id).get();
    }

    public Booking findByUserAndTours(Booking booking) {
        return bookingRepository.findByUserAndTours(booking.getUser(), booking.getTours());
    }
}
