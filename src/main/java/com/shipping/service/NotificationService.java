package com.shipping.service;

import com.shipping.model.Booking;
import java.net.URI;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
  private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

  private final boolean smsEnabled;
  private final String accountSid;
  private final String authToken;
  private final String fromNumber;
  private final RestTemplate restTemplate = new RestTemplate();

  public NotificationService(
      @Value("${notify.sms.enabled:false}") boolean smsEnabled,
      @Value("${notify.sms.accountSid:}") String accountSid,
      @Value("${notify.sms.authToken:}") String authToken,
      @Value("${notify.sms.fromNumber:}") String fromNumber) {
    this.smsEnabled = smsEnabled;
    this.accountSid = accountSid;
    this.authToken = authToken;
    this.fromNumber = fromNumber;
  }

  public void bookingCreated(Booking booking) {
    String msg =
        "Thanks " + booking.getCustomerName()
            + "! We received your viewing request for "
            + Optional.ofNullable(booking.getProductName()).orElse("an item")
            + " on " + booking.getPreferredDate() + " at " + booking.getPreferredTime()
            + ". We'll confirm shortly.";
    sendSms(booking.getCustomerPhone(), msg);
  }

  public void bookingStatusChanged(Booking booking) {
    String status = booking.getStatus().name();
    String reason = Optional.ofNullable(booking.getStatusReason()).filter(r -> !r.isBlank()).orElse(null);
    StringBuilder msg = new StringBuilder("Update on your booking: ").append(status);
    if (reason != null) {
      msg.append(". ").append(reason);
    }
    sendSms(booking.getCustomerPhone(), msg.toString());
  }

  private void sendSms(String to, String body) {
    if (!smsEnabled) {
      log.info("[notify] SMS disabled. Would send to {}: {}", to, body);
      return;
    }
    if (accountSid.isBlank() || authToken.isBlank() || fromNumber.isBlank()) {
      log.warn("[notify] SMS enabled but credentials missing; skipping send.");
      return;
    }
    try {
      URI uri = URI.create(String.format("https://api.twilio.com/2010-04-01/Accounts/%s/Messages.json", accountSid));
      MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
      form.add("To", to);
      form.add("From", fromNumber);
      form.add("Body", body);

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      headers.setBasicAuth(accountSid, authToken);

      restTemplate.postForEntity(uri, new HttpEntity<>(form, headers), String.class);
      log.info("[notify] SMS sent to {}", to);
    } catch (Exception ex) {
      log.warn("[notify] Failed to send SMS to {}: {}", to, ex.getMessage());
    }
  }
}
