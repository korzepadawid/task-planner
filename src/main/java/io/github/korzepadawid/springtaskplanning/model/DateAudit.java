package io.github.korzepadawid.springtaskplanning.model;

import java.time.ZonedDateTime;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Embeddable
public class DateAudit {

  private ZonedDateTime updatedAt;
  private ZonedDateTime createdAt;

  @PrePersist
  void prePersist() {
    createdAt = ZonedDateTime.now();
  }

  @PreUpdate
  void preUpdate() {
    updatedAt = ZonedDateTime.now();
  }

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
