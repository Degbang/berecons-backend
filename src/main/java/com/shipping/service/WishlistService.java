package com.shipping.service;

import com.shipping.dto.WishlistRequestDto;
import com.shipping.model.WishlistRequest;
import com.shipping.model.WishlistStatus;
import com.shipping.repository.WishlistRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WishlistService {
  private final WishlistRepository wishlistRepository;

  public WishlistService(WishlistRepository wishlistRepository) {
    this.wishlistRepository = wishlistRepository;
  }

  public List<WishlistRequest> list(Optional<WishlistStatus> status) {
    return status.map(wishlistRepository::findByStatus).orElseGet(wishlistRepository::findAll);
  }

  public WishlistRequest create(WishlistRequestDto request) {
    WishlistRequest entity = new WishlistRequest();
    entity.setCustomerName(request.getCustomerName());
    entity.setCustomerPhone(request.getCustomerPhone());
    entity.setWhatsappNumber(request.getWhatsappNumber());
    entity.setDesiredItems(request.getDesiredItems());
    entity.setNotes(request.getNotes());
    return wishlistRepository.save(entity);
  }

  public WishlistRequest updateStatus(Long id, String statusValue) {
    WishlistRequest request = wishlistRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist request not found"));
    request.setStatus(parseStatus(statusValue));
    return wishlistRepository.save(request);
  }

  private WishlistStatus parseStatus(String value) {
    try {
      return WishlistStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid wishlist status");
    }
  }
}
