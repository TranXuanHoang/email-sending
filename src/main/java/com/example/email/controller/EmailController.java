package com.example.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.email.properties.EmailProperties;
import com.example.email.service.EmailService;

@RestController
public class EmailController {
  @Autowired
  private EmailProperties emailProperties;

  @Autowired
  private EmailService emailService;

  @GetMapping("/mail-properties")
  public EmailProperties getEmailProperties() {
    return emailProperties;
  }

  @GetMapping("/mail-send")
  public String sendMail() {
    try {
      String[] inputs = new String[] { "123456",
          "The payment transaction was failed. 決済に失敗しました。" };
      emailService.createEmailMessage(inputs);
      return "Sent mail successfully";
    } catch (Exception e) {
      e.printStackTrace();
      return "Failed to send mail";
    }
  }
}