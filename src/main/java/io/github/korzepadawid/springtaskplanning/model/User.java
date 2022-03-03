package io.github.korzepadawid.springtaskplanning.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity {

  @Embedded private final DateAudit dateAudit = new DateAudit();

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

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
  private Avatar avatar;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private final Set<TaskList> taskLists = new HashSet<>();

  public User() {
    super();
  }

  public void addTaskList(TaskList taskList) {
    taskList.setUser(this);
    taskLists.add(taskList);
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

  public Set<TaskList> getTaskLists() {
    return taskLists;
  }

  public Avatar getAvatar() {
    return avatar;
  }

  public void setAvatar(Avatar avatar) {
    this.avatar = avatar;
  }
}
