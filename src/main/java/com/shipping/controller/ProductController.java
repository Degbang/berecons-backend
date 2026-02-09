package com.shipping.controller;

import com.shipping.dto.ProductRequest;
import com.shipping.dto.StatusUpdateRequest;
import com.shipping.model.Product;
import com.shipping.model.ProductStatus;
import com.shipping.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> list(@RequestParam Optional<ProductStatus> status) {
    return productService.list(status);
  }

  @GetMapping("/{id}")
  public Product get(@PathVariable Long id) {
    return productService.get(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@Valid @RequestBody ProductRequest request) {
    return productService.create(request);
  }

  @PutMapping("/{id}")
  public Product update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
    return productService.update(id, request);
  }

  @PatchMapping("/{id}/status")
  public Product updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
    return productService.updateStatus(id, request.getStatus());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    productService.delete(id);
  }
}
