package io.github.korzepadawid.springtaskplanning.dto;

import io.github.korzepadawid.springtaskplanning.model.User;
import java.time.ZonedDateTime;

public class UserResponse {

  private Long id;
  private String name;
  private String email;
  private String avatarUrl;
  private String authProvider;
  private ZonedDateTime memberSince;

  public UserResponse() {}

  public UserResponse(User user) {
    if (user != null) {
      this.id = user.getId();
      this.name = user.getName();
      this.email = user.getEmail();
      this.authProvider = user.getAuthProvider().toString();
      this.memberSince = user.getDateAudit().getCreatedAt();
      if (user.getAvatar() == null) {
        this.avatarUrl = null;
      } else {
        this.avatarUrl = "/api/v1/users/" + id + "/avatar";
      }
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public String getAuthProvider() {
    return authProvider;
  }

  public void setAuthProvider(String authProvider) {
    this.authProvider = authProvider;
  }

  public ZonedDateTime getMemberSince() {
    return memberSince;
  }

  public void setMemberSince(ZonedDateTime memberSince) {
    this.memberSince = memberSince;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }
}
