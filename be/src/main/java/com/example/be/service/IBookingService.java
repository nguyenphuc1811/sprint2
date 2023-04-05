package com.example.be.service;

import com.example.be.dto.tours.BookingDto;
import com.example.be.dto.tours.IToursDto;
import com.example.be.model.tours.Booking;
import com.example.be.model.tours.Tours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IBookingService {
    boolean addBooking(Booking booking);

    boolean removeBooking(int id);

    Booking getById(int id);

    void deleteByUserIdAndToursId(int userId, int toursId);

    List<Booking> findAllByPaymentAndUser(int id, boolean check);

    void saveQuantity(List<Booking> bookingList);

    void payment(List<Booking> bookingList);

    List<IToursDto> checkRemaining(List<Booking> bookingList);

    Page<Booking> getDetailPayment(int id, Pageable pageable);
}
