package io.github.korzepadawid.springtaskplanning.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity implements Serializable {

  @NotBlank
  @Size(min = 3, max = 255)
  private String name;

  @Email
  @NotBlank
  @Column(unique = true)
  @Size(min = 3, max = 320)
  private String email;

  @NotBlank
  @Size(max = 72)
  private String password;

  @Enumerated(EnumType.STRING)
  private AuthProvider authProvider;

  @Embedded private final DateAudit dateAudit = new DateAudit();

  public User() {}

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

  public AuthProvider getAuthProvider() {
    return authProvider;
  }

  public void setAuthProvider(AuthProvider authProvider) {
    this.authProvider = authProvider;
  }

  public DateAudit getDateAudit() {
    return dateAudit;
  }
}
