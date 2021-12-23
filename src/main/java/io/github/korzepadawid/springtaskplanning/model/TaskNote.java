package io.github.korzepadawid.springtaskplanning.model;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "task_notes")
public class TaskNote extends AbstractBaseEntity {

  @NotNull
  @Size(min = 1, max = 255)
  private String note;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_id")
  private Task task;

  @Embedded private final DateAudit dateAudit = new DateAudit();

  public TaskNote() {
    super();
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public DateAudit getDateAudit() {
    return dateAudit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskNote taskNote = (TaskNote) o;
    return Objects.equals(note, taskNote.note);
  }

  @Override
  public int hashCode() {
    return Objects.hash(note);
  }
}
