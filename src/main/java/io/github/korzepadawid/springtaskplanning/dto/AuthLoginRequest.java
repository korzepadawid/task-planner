package io.github.korzepadawid.springtaskplanning.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthLoginRequest {

  @Email
  @NotBlank
  @Size(min = 3, max = 320)
  private String email;

  @NotBlank
  @Size(max = 72)
  private String password;

  public AuthLoginRequest() {}

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
