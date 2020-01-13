package com.example.oauth2.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

/**
 * Manual configuration
 */
@Configuration
@Slf4j
public class AppConfig {

  //@Value("${spring.security.oauth2.client.registration.google.client-id}")
  private String clientId;
  //@Value("${spring.security.oauth2.client.registration.google.client-secret}")
  private String clientSecret;

  //@Bean
  public ClientRegistrationRepository clientRegistrationRepository() {

    log.info("clientId: {}", clientId);
    log.info("clientSecret: {}", clientSecret);

    ClientRegistration clientRegistration = CommonOAuth2Provider.GOOGLE.getBuilder("google")
        .clientId(clientId)
        .clientSecret(clientSecret)
        .build();

    return new InMemoryClientRegistrationRepository(clientRegistration);
  }

}
