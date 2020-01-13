package com.example.oauth2.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        //.antMatchers("/oauth_login")
        //.permitAll()
        .anyRequest().authenticated()
        .and()
        //.openidLogin()
        .oauth2Login()
//          .defaultSuccessUrl("/")
          .failureUrl("/loginFailure")
//          .authorizationEndpoint()
//          .baseUri("/oauth2/authorization")
//          .authorizationRequestRepository(authorizationRequestRepository())
//          .and()
//          .tokenEndpoint()
//          .accessTokenResponseClient(accessTokenResponseClient())
//          .and()
//          .redirectionEndpoint()
//          .baseUri("/login/oauth2/code") // This is the endpoint to redirect to after authentication
                        // with the external provider. Default: login/oauth2/code.
//        .and()
//        .userInfoEndpoint()
    //.loginPage("/oauth_login")
    ;
  }

  /**
   * Spring-provided implementation for our bean, but we could also provide
   * a custom one.
   *
   * @return AuthorizationRequestRepository
   */
  //@Bean
  public AuthorizationRequestRepository<OAuth2AuthorizationRequest>
    authorizationRequestRepository() {

    return new HttpSessionOAuth2AuthorizationRequestRepository();
  }

  /**
   * This configuration is the same as the default one and is using the Spring
   * implementation which is based on exchanging an authorization code with
   * the provider.
   * <p>
   * Of course, we could also substitute a custom response client.
   *
   * @return OAuth2AccessTokenResponseClient
   */
  //@Bean
  public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
    accessTokenResponseClient() {

    return new DefaultAuthorizationCodeTokenResponseClient();
  }
}
