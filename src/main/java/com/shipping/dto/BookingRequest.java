package com.shipping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookingRequest {
  @NotBlank
  private String customerName;
  @NotBlank
  private String customerPhone;
  private String whatsappNumber;
  private Long productId;
  private String productName;
  @NotNull
  private LocalDate preferredDate;
  @NotNull
  private LocalTime preferredTime;
  private String notes;

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getWhatsappNumber() {
    return whatsappNumber;
  }

  public void setWhatsappNumber(String whatsappNumber) {
    this.whatsappNumber = whatsappNumber;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public LocalDate getPreferredDate() {
    return preferredDate;
  }

  public void setPreferredDate(LocalDate preferredDate) {
    this.preferredDate = preferredDate;
  }

  public LocalTime getPreferredTime() {
    return preferredTime;
  }

  public void setPreferredTime(LocalTime preferredTime) {
    this.preferredTime = preferredTime;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
