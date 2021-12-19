package io.github.korzepadawid.springtaskplanning.dto;

import io.github.korzepadawid.springtaskplanning.model.Task;
import java.time.ZonedDateTime;

public class TaskShortResponse {

  private Long id;
  private String title;
  private Boolean done;
  private ZonedDateTime deadline;
  private ZonedDateTime createdAt;

  public TaskShortResponse() {}

  public TaskShortResponse(Task task) {
    if (task != null) {
      this.id = task.getId();
      this.title = task.getTitle();
      this.done = task.getDone();
      this.deadline = task.getDeadline();
      this.createdAt = task.getDateAudit().getCreatedAt();
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getDone() {
    return done;
  }

  public void setDone(Boolean done) {
    this.done = done;
  }

  public ZonedDateTime getDeadline() {
    return deadline;
  }

  public void setDeadline(ZonedDateTime deadline) {
    this.deadline = deadline;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
