package io.github.korzepadawid.springtaskplanning.model;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tasks")
public class Task extends AbstractBaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_list_id")
  private TaskList taskList;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "task")
  private TaskNote taskNote;

  @NotBlank
  @Size(min = 3, max = 255)
  private String title;

  @NotNull private Boolean done;

  @NotNull @Future private ZonedDateTime deadline;

  @Embedded private final DateAudit dateAudit = new DateAudit();

  public Task() {
    super();
  }

  public void addNote(TaskNote taskNote) {
    this.taskNote = taskNote;
    taskNote.setTask(this);
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public TaskList getTaskList() {
    return taskList;
  }

  public void setTaskList(TaskList taskList) {
    this.taskList = taskList;
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

  public DateAudit getDateAudit() {
    return dateAudit;
  }

  public TaskNote getTaskNote() {
    return taskNote;
  }

  public void setTaskNote(TaskNote taskNote) {
    this.taskNote = taskNote;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Task task = (Task) o;
    return title.equals(task.title) && done.equals(task.done) && deadline.equals(task.deadline);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, done, deadline);
  }
}
