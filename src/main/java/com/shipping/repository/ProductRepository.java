package com.shipping.repository;

import com.shipping.model.Product;
import com.shipping.model.ProductStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByStatus(ProductStatus status);
}
