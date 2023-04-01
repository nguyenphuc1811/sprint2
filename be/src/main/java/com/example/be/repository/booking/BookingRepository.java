package com.example.be.repository.booking;

import com.example.be.model.tours.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByPaymentAndUserId(boolean payment, Integer user_id);


    Boolean existsByUserIdAndToursIdAndPaymentIsFalse(Integer user_id, Integer tours_id);

    boolean existsById(Integer id);

    @Transactional
    @Modifying
    void deleteByUserIdAndToursId(Integer user_id, Integer tours_id);

    @Transactional
    @Modifying
    @Query(value = "update `booking` set slot = :slot , payment = :payment  where id = :id", nativeQuery = true)
    void updateBooking(@Param("slot") int slot, @Param("payment") boolean payment, @Param("id") int id);
}
