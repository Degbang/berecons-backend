package com.shipping.service;

import com.shipping.dto.ProductRequest;
import com.shipping.model.Product;
import com.shipping.model.ProductStatus;
import com.shipping.repository.ProductRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> list(Optional<ProductStatus> status) {
    return status.map(productRepository::findByStatus).orElseGet(productRepository::findAll);
  }

  public Product get(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
  }

  public Product create(ProductRequest request) {
    Product product = new Product();
    applyRequest(product, request);
    return productRepository.save(product);
  }

  public Product update(Long id, ProductRequest request) {
    Product product = get(id);
    applyRequest(product, request);
    return productRepository.save(product);
  }

  public void delete(Long id) {
    if (!productRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }
    productRepository.deleteById(id);
  }

  public Product updateStatus(Long id, String statusValue) {
    Product product = get(id);
    product.setStatus(parseStatus(statusValue));
    return productRepository.save(product);
  }

  private void applyRequest(Product product, ProductRequest request) {
    product.setName(request.getName());
    product.setBrand(request.getBrand());
    product.setCategory(request.getCategory());
    product.setConditionNote(request.getConditionNote());
    product.setPrice(request.getPrice());
    product.setCurrency(request.getCurrency());
    product.setDescription(request.getDescription());
    product.setImageUrls(request.getImageUrls());
    if (request.getStatus() != null && !request.getStatus().isBlank()) {
      product.setStatus(parseStatus(request.getStatus()));
    }
  }

  private ProductStatus parseStatus(String value) {
    try {
      return ProductStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product status");
    }
  }
}
