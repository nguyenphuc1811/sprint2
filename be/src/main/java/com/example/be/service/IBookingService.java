package com.example.be.service;

import com.example.be.model.tours.Booking;
import com.example.be.model.user.User;

import java.util.List;

public interface IBookingService {
    boolean addBooking(Booking booking);

    public List<Booking> getListCard(boolean check, User user);

    boolean removeBooking(int id);

    Booking getById(int id);

    Booking findByUserAndTours(Booking booking);
}
