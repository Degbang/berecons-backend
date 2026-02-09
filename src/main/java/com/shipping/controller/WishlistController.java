package com.shipping.controller;

import com.shipping.dto.StatusUpdateRequest;
import com.shipping.dto.WishlistRequestDto;
import com.shipping.model.WishlistRequest;
import com.shipping.model.WishlistStatus;
import com.shipping.service.WishlistService;
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
@RequestMapping("/api/wishlists")
public class WishlistController {
  private final WishlistService wishlistService;

  public WishlistController(WishlistService wishlistService) {
    this.wishlistService = wishlistService;
  }

  @GetMapping
  public List<WishlistRequest> list(@RequestParam Optional<WishlistStatus> status) {
    return wishlistService.list(status);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WishlistRequest create(@Valid @RequestBody WishlistRequestDto request) {
    return wishlistService.create(request);
  }

  @PatchMapping("/{id}/status")
  public WishlistRequest updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
    return wishlistService.updateStatus(id, request.getStatus());
  }
}
