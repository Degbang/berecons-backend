package com.shipping.repository;

import com.shipping.model.Booking;
import com.shipping.model.BookingStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> findByStatus(BookingStatus status);
}
