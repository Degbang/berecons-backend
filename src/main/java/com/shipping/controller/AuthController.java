package com.shipping.controller;

import com.shipping.config.AdminProperties;
import com.shipping.dto.LoginRequest;
import com.shipping.dto.LoginResponse;
import com.shipping.dto.ResetRequest;
import com.shipping.service.AdminUserService;
import com.shipping.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final AdminUserService adminUserService;
  private final AdminProperties adminProperties;

  public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
      AdminUserService adminUserService, AdminProperties adminProperties) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.adminUserService = adminUserService;
    this.adminProperties = adminProperties;
  }

  @PostMapping("/login")
  public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    if (!authentication.isAuthenticated()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed");
    }
    String token = jwtService.generateToken(request.getUsername());
    return new LoginResponse(token, request.getUsername());
  }

  @PostMapping("/reset")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void reset(@Valid @RequestBody ResetRequest request) {
    if (!adminProperties.getResetKey().equals(request.getResetKey())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid reset key");
    }
    adminUserService.resetPassword(request.getNewUsername(), request.getNewPassword());
  }
}
