package com.example.be.service.impl;

import com.example.be.dto.tours.BookingDto;
import com.example.be.dto.tours.IToursDto;
import com.example.be.model.tours.Booking;
import com.example.be.model.tours.Tours;
import com.example.be.model.user.User;
import com.example.be.repository.booking.BookingRepository;
import com.example.be.repository.tours.ToursRepository;
import com.example.be.service.IBookingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BookingServiceImpl implements IBookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ToursRepository toursRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
    public List<Booking> findAllByPaymentAndUser(int id, boolean check) {
        return bookingRepository.findAllByPaymentAndUserId(check, id);
    }

    @Override
    public Page<Booking> getDetailPayment(int id, Pageable pageable) {
        return bookingRepository.findAllByPaymentIsTrueAndUserId(id, pageable);
    }

    @Override
    public void saveQuantity(List<Booking> bookingList) {
        for (Booking booking : bookingList) {
            bookingRepository.updateBooking(
                    booking.getSlot(), false, booking.getId(), "");
        }
    }

    @Override
    public void payment(List<Booking> bookingList) {
        LocalDateTime dateTime = LocalDateTime.now();
        String dateBooking = dateTime.format(formatter);
        for (Booking booking : bookingList) {
            if (booking.getSlot() > 0) {
                bookingRepository.updateBooking(
                        booking.getSlot(), true, booking.getId(), dateBooking);
            }
        }
    }

    @Override
    public List<IToursDto> checkRemaining(List<Booking> bookingList) {
        List<IToursDto> toursList = new LinkedList<>();
        int remaining;
        for (Booking booking : bookingList) {
            remaining = toursRepository.findByIdDto(booking.getTours().getId()).getRemaining();
            if (booking.getSlot() > remaining) {
                toursList.add(toursRepository.findByIdDto(booking.getTours().getId()));
            }
        }
        return toursList;
    }
}
