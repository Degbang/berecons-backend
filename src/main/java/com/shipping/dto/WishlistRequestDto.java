package com.shipping.dto;

import jakarta.validation.constraints.NotBlank;

public class WishlistRequestDto {
  @NotBlank
  private String customerName;
  @NotBlank
  private String customerPhone;
  private String whatsappNumber;
  @NotBlank
  private String desiredItems;
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

  public String getDesiredItems() {
    return desiredItems;
  }

  public void setDesiredItems(String desiredItems) {
    this.desiredItems = desiredItems;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
