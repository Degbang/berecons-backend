package com.shipping.dto;

import jakarta.validation.constraints.NotBlank;

public class ResetRequest {
  @NotBlank
  private String resetKey;

  @NotBlank
  private String newUsername;

  @NotBlank
  private String newPassword;

  public String getResetKey() {
    return resetKey;
  }

  public void setResetKey(String resetKey) {
    this.resetKey = resetKey;
  }

  public String getNewUsername() {
    return newUsername;
  }

  public void setNewUsername(String newUsername) {
    this.newUsername = newUsername;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}
