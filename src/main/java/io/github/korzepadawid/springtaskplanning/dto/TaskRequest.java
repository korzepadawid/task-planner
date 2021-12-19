package io.github.korzepadawid.springtaskplanning.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;

public class TaskRequest {

  @Size(min = 1, max = 70)
  private String title;

  @Size(max = 255)
  private String note;

  @Future private ZonedDateTime deadline;

  public TaskRequest() {}

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public ZonedDateTime getDeadline() {
    return deadline;
  }

  public void setDeadline(ZonedDateTime deadline) {
    this.deadline = deadline;
  }
}
