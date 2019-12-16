package com.example.email.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Defines and reads properties needed to setup the email sending.
 * 
 * @author hoang.tran
 */
@Component
@PropertySources({
    @PropertySource(value = "file:./config/email.properties", encoding = "UTF-8", ignoreResourceNotFound = true) })
@ConfigurationProperties(prefix = "email")
@Data
public class EmailProperties {
  public Sender sender;
  public Receiver receiver;

  @Data
  public static class Sender {
    public String host;
    public int port;
    public String username;
    public String password;
    public String protocol;
    public boolean smtpAuth;
    public boolean smtpStarttlsEnable;
    public boolean debug;
    public String connectiontimeout;
    public String timeout;
    public String writetimeout;
  }

  @Data
  public static class Receiver {
    public String subject;
    public String contents;
    public Map<String, String> emailAddresses;
    public String environment;
  }
}