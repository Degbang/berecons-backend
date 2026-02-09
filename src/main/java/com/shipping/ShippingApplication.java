package com.shipping;

import com.shipping.config.AdminProperties;
import com.shipping.service.AdminUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(AdminProperties.class)
public class ShippingApplication {
  public static void main(String[] args) {
    SpringApplication.run(ShippingApplication.class, args);
  }

  @Bean
  CommandLineRunner seedAdmin(AdminUserService adminUserService, AdminProperties props) {
    return args -> adminUserService.ensureAdminUser(props.getUsername(), props.getPassword());
  }
}
