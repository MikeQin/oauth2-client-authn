package com.example.oauth2.client;

import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE )
public class HomeController {

  private OAuth2AuthorizedClientService authorizedClientService;

  public HomeController(OAuth2AuthorizedClientService authorizedClientService) {
    this.authorizedClientService = authorizedClientService;
  }

  @GetMapping("/")
  public ResponseEntity<String> main(OAuth2AuthenticationToken authentication) {
    String res = "<h1>Welcome to OAuth2 Client Authentication!</h1>";
    User user = getLoginInfo(authentication);
    res += "<p>" + user.toString() + "</p>";

    return ResponseEntity.ok(res);
  }

  @GetMapping("/loginFailure")
  public ResponseEntity<String> loginFailure() {
    String res = "<h1 style='color:red;'>Failed to Authenticate!</h1>";

    return ResponseEntity.ok(res);
  }

  public User getLoginInfo(OAuth2AuthenticationToken authentication) {

    OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
    authentication.getAuthorizedClientRegistrationId(),
        authentication.getName());

    String userInfoEndpointUri = client.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUri();

    User user = new User();
    if (!StringUtils.isEmpty(userInfoEndpointUri)) {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
          .getTokenValue());
      HttpEntity entity = new HttpEntity("", headers);
      ResponseEntity <Map>response = restTemplate
          .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
      Map userAttributes = response.getBody();
      user.setName(userAttributes.get("name").toString());
      user.setEmail(userAttributes.get("email").toString());
    }

    return user;
  }
}
