package io.github.korzepadawid.springtaskplanning.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "avatars")
public class Avatar extends AbstractBaseEntity {

  @OneToOne private User user;

  @Embedded private DateAudit dateAudit = new DateAudit();

  @Size(max = 255)
  private String storageKey;

  public Avatar() {}

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public DateAudit getDateAudit() {
    return dateAudit;
  }

  public void setDateAudit(DateAudit dateAudit) {
    this.dateAudit = dateAudit;
  }

  public String getStorageKey() {
    return storageKey;
  }

  public void setStorageKey(String storageKey) {
    this.storageKey = storageKey;
  }
}
