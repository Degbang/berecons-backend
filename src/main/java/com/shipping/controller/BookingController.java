package com.shipping.controller;

import com.shipping.dto.BookingRequest;
import com.shipping.dto.StatusUpdateRequest;
import com.shipping.model.Booking;
import com.shipping.model.BookingStatus;
import com.shipping.service.BookingService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping
  public List<Booking> list(@RequestParam Optional<BookingStatus> status) {
    return bookingService.list(status);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Booking create(@Valid @RequestBody BookingRequest request) {
    return bookingService.create(request);
  }

  @PatchMapping("/{id}/status")
  public Booking updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
    return bookingService.updateStatus(id, request.getStatus(), request.getReason());
  }
}
