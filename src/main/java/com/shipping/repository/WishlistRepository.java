package com.shipping.repository;

import com.shipping.model.WishlistRequest;
import com.shipping.model.WishlistStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<WishlistRequest, Long> {
  List<WishlistRequest> findByStatus(WishlistStatus status);
}
