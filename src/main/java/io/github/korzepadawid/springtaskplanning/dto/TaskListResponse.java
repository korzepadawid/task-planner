package io.github.korzepadawid.springtaskplanning.dto;

import io.github.korzepadawid.springtaskplanning.model.TaskList;
import java.time.ZonedDateTime;

public class TaskListResponse {

  private Long id;
  private String title;
  private ZonedDateTime createdAt;
  private Integer undone;
  private Integer done;
  private Integer total;

  public TaskListResponse(TaskList taskList) {
    if (taskList != null) {
      this.id = taskList.getId();
      this.title = taskList.getTitle();
      this.createdAt = taskList.getDateAudit().getCreatedAt();
      this.total = taskList.getTasks().size();
      this.undone = 0;
      this.done = 0;
      taskList
          .getTasks()
          .forEach(
              task -> {
                if (task.getDone()) {
                  this.done++;
                } else {
                  this.undone++;
                }
              });
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

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Integer getUndone() {
    return undone;
  }

  public void setUndone(Integer undone) {
    this.undone = undone;
  }

  public Integer getDone() {
    return done;
  }

  public void setDone(Integer done) {
    this.done = done;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }
}
