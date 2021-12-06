package io.github.korzepadawid.springtaskplanning.dto;

public class AuthLoginResponse {

  private String accessToken;

  public AuthLoginResponse() {}

  public AuthLoginResponse(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
