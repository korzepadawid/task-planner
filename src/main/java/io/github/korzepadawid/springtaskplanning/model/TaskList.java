package io.github.korzepadawid.springtaskplanning.model;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "task_lists")
public class TaskList extends AbstractBaseEntity {

  @NotBlank
  @Size(min = 3, max = 255)
  private String title;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Embedded private final DateAudit dateAudit = new DateAudit();

  public TaskList() {}

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
    TaskList taskList = (TaskList) o;
    return title.equals(taskList.title) && dateAudit.equals(taskList.dateAudit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, dateAudit);
  }
}
