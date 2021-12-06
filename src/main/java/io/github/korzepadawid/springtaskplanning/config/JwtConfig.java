package io.github.korzepadawid.springtaskplanning.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt.config")
public class JwtConfig {

  private String secret;
  private Long accessTokenExpirationInSeconds;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public Long getAccessTokenExpirationInSeconds() {
    return accessTokenExpirationInSeconds;
  }

  public void setAccessTokenExpirationInSeconds(Long accessTokenExpirationInSeconds) {
    this.accessTokenExpirationInSeconds = accessTokenExpirationInSeconds;
  }
}
