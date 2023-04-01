package com.example.be.service.impl;

import com.example.be.model.tours.Booking;
import com.example.be.model.tours.Tours;
import com.example.be.model.user.User;
import com.example.be.repository.booking.BookingRepository;
import com.example.be.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements IBookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public boolean addBooking(Booking booking) {
        if (bookingRepository.existsByUserIdAndToursIdAndPaymentIsFalse(booking.getUser().getId(), booking.getTours().getId())) {
            return false;
        }
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public boolean removeBooking(int id) {
        if (getById(id) != null) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Booking getById(int id) {
        return bookingRepository.findById(id).get();
    }

    @Override
    public void deleteByUserIdAndToursId(int userId, int toursId) {
        bookingRepository.deleteByUserIdAndToursId(userId, toursId);
    }

    @Override
    public List<Booking> findAllByPaymentAndUser(int id) {
        return bookingRepository.findAllByPaymentAndUserId(false, id);
    }

    @Override
    public void saveQuantity(List<Booking> bookingList) {
        for (Booking booking : bookingList) {
            bookingRepository.updateBooking(
                    booking.getSlot(), false, booking.getId());
        }
    }

    @Override
    public void payment(List<Booking> bookingList) {
        for (Booking booking : bookingList) {
            bookingRepository.updateBooking(
                    booking.getSlot(), true, booking.getId());
        }
    }
}
