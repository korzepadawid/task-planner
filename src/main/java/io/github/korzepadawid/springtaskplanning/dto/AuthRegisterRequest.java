package io.github.korzepadawid.springtaskplanning.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthRegisterRequest {

  @NotBlank
  @Size(min = 3, max = 255)
  private String name;

  @Email
  @NotBlank
  @Size(min = 3, max = 320)
  private String email;

  @NotBlank
  @Size(max = 72)
  private String password;

  public AuthRegisterRequest() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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
