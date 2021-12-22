package io.github.korzepadawid.springtaskplanning.dto;

import io.github.korzepadawid.springtaskplanning.model.Task;
import java.time.ZonedDateTime;

public class TaskLongResponse {

  private Long id;
  private String title;
  private Boolean done;
  private ZonedDateTime deadline;
  private ZonedDateTime createdAt;
  private String note;

  public TaskLongResponse() {}

  public TaskLongResponse(Task task) {
    if (task != null) {
      this.id = task.getId();
      this.title = task.getTitle();
      this.done = task.getDone();
      this.deadline = task.getDeadline();
      this.createdAt = task.getDateAudit().getCreatedAt();

      if (task.getTaskNote() != null) {
        this.note = task.getTaskNote().getNote();
      } else {
        this.note = null;
      }
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

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
