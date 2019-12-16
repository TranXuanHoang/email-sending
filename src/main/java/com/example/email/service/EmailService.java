package com.example.email.service;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.email.properties.EmailProperties;
import com.example.email.util.MessageUtil;

/**
 * Provides service methods to setup and send email.
 * 
 * @author hoang.tran
 */
@Component
public class EmailService {
  private EmailProperties properties;
  private JavaMailSender emailSender;

  public EmailService(EmailProperties properties) {
    // Get properties to setup email sending
    this.properties = properties;System.out.println(">>>> " + properties);

    // Setup properties for the email sending
    if (properties.sender != null && properties.receiver != null) {
      this.emailSender = getJavaMailSender();
    }
  }

  /**
   * Sets up the object to send the email messages.
   * 
   * @return a <code>JavaMailSender</code> object that will actually send
   *         emails.
   */
  private JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(properties.sender.host);
    mailSender.setPort(properties.sender.port);

    // Uncomment the following two commands if the mail server
    // requires us to set the username and password
    // mailSender.setUsername("my.email@mail-server.com");
    // mailSender.setPassword("password-to-login-mail-account");

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", properties.sender.protocol);
    props.put("mail.smtp.auth", properties.sender.smtpAuth);
    props.put("mail.smtp.starttls.enable",
        properties.sender.smtpStarttlsEnable);
    props.put("mail.debug", properties.sender.debug);

    props.put("mail.smtp.connectiontimeout",
        properties.sender.connectiontimeout);
    props.put("mail.smtp.timeout", properties.sender.timeout);
    props.put("mail.smtp.writetimeout", properties.sender.writetimeout);

    return mailSender;
  }

  /**
   * Creates and sends emails.
   * 
   * @param inputs
   *          values to replace the <code>%parameter%</code> parts of the email
   *          template.
   * @throws MessagingException
   *           if the creation of the email failed.
   */
  public void createEmailMessage(Object[] inputs) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();

    // Use the true flag to indicate you need a multi-part message
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    // Set sender
    helper.setFrom(properties.sender.username);

    // Set receivers
    String emailAddresses = properties.receiver.emailAddresses
        .get(properties.receiver.environment);
    String[] emails = emailAddresses.split(",[ ]*");
    helper.setTo(emails);

    // Set subject
    helper.setSubject(properties.receiver.subject);

    // Set body, use the true flag to indicate the text included is HTML
    String contents = properties.receiver.contents;
    if (inputs != null) {
      contents = MessageUtil.format(contents, inputs);
    }
    helper.setText(contents, true);

    // Send mail
    emailSender.send(message);
  }
}