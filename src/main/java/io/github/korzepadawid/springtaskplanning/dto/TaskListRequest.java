package io.github.korzepadawid.springtaskplanning.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TaskListRequest {

  @NotNull
  @Size(min = 3, max = 255)
  private String title;

  public TaskListRequest() {}

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
