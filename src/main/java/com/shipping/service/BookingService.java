package com.shipping.service;

import com.shipping.dto.BookingRequest;
import com.shipping.model.Booking;
import com.shipping.model.BookingStatus;
import com.shipping.repository.BookingRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {
  private final BookingRepository bookingRepository;
  private final NotificationService notificationService;

  public BookingService(BookingRepository bookingRepository, NotificationService notificationService) {
    this.bookingRepository = bookingRepository;
    this.notificationService = notificationService;
  }

  public List<Booking> list(Optional<BookingStatus> status) {
    return status.map(bookingRepository::findByStatus).orElseGet(bookingRepository::findAll);
  }

  public Booking create(BookingRequest request) {
    Booking booking = new Booking();
    booking.setCustomerName(request.getCustomerName());
    booking.setCustomerPhone(request.getCustomerPhone());
    booking.setWhatsappNumber(request.getWhatsappNumber());
    booking.setProductId(request.getProductId());
    booking.setProductName(request.getProductName());
    booking.setPreferredDate(request.getPreferredDate());
    booking.setPreferredTime(request.getPreferredTime());
    booking.setNotes(request.getNotes());
    Booking saved = bookingRepository.save(booking);
    notificationService.bookingCreated(saved);
    return saved;
  }

  public Booking updateStatus(Long id, String statusValue, String reason) {
    Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
    booking.setStatus(parseStatus(statusValue));
    booking.setStatusReason(reason);
    Booking saved = bookingRepository.save(booking);
    notificationService.bookingStatusChanged(saved);
    return saved;
  }

  private BookingStatus parseStatus(String value) {
    try {
      return BookingStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid booking status");
    }
  }
}
