package com.shipping;

import com.shipping.config.AdminProperties;
import com.shipping.service.AdminUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

@SpringBootApplication
@EnableConfigurationProperties(AdminProperties.class)
public class ShippingApplication {
  public static void main(String[] args) {
    SpringApplication.run(ShippingApplication.class, args);
  }

  @Bean
  CommandLineRunner seedAdmin(AdminUserService adminUserService, AdminProperties props, Environment env) {
    return args -> {
      if (env.acceptsProfiles(Profiles.of("prod"))) {
        if (isDefaultAdmin(props)) {
          throw new IllegalStateException("Admin credentials and reset key must be set in production.");
        }
      }
      adminUserService.ensureAdminUser(props.getUsername(), props.getPassword());
    };
  }

  private boolean isDefaultAdmin(AdminProperties props) {
    return "admin".equalsIgnoreCase(props.getUsername())
        || "admin123".equals(props.getPassword())
        || "change-me".equals(props.getResetKey());
  }
}
