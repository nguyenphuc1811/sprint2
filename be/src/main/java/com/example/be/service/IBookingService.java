package com.example.be.service;

import com.example.be.model.tours.Booking;
import com.example.be.model.user.User;

import java.util.List;

public interface IBookingService {
    boolean addBooking(Booking booking);

    boolean removeBooking(int id);

    Booking getById(int id);

    void deleteByUserIdAndToursId(int userId, int toursId);
   
    List<Booking> findAllByPaymentAndUser(int id);
    void saveQuantity(List<Booking> bookingList);
}
