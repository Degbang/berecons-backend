package com.shipping.service;

import com.shipping.model.AdminUser;
import com.shipping.repository.AdminUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminUserService implements UserDetailsService {
  private final AdminUserRepository adminUserRepository;
  private final PasswordEncoder passwordEncoder;

  public AdminUserService(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
    this.adminUserRepository = adminUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AdminUser admin = adminUserRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Admin user not found"));

    return User.withUsername(admin.getUsername())
        .password(admin.getPasswordHash())
        .roles("ADMIN")
        .build();
  }

  public void ensureAdminUser(String username, String password) {
    if (username == null || username.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin username is required");
    }
    if (password == null || password.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin password is required");
    }

    if (adminUserRepository.existsByUsername(username)) {
      return;
    }

    AdminUser admin = new AdminUser();
    admin.setUsername(username.trim());
    admin.setPasswordHash(passwordEncoder.encode(password));
    adminUserRepository.save(admin);
  }

  public void resetPassword(String username, String newPassword) {
    if (username == null || username.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
    }
    if (newPassword == null || newPassword.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password is required");
    }

    AdminUser admin = adminUserRepository.findByUsername(username.trim())
        .orElseGet(() -> {
          AdminUser created = new AdminUser();
          created.setUsername(username.trim());
          return created;
        });

    admin.setPasswordHash(passwordEncoder.encode(newPassword));
    adminUserRepository.save(admin);
  }
}
