package com.example.be.repository.booking;

import com.example.be.model.tours.Booking;
import com.example.be.model.tours.Tours;
import com.example.be.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByPaymentAndUser(boolean payment, User user);

    Boolean existsByUserAndTours(User user, Tours tours);

    Booking findByUserAndTours(User user, Tours tours);
}
