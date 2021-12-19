package io.github.korzepadawid.springtaskplanning.util;

import io.github.korzepadawid.springtaskplanning.dto.TaskRequest;
import io.github.korzepadawid.springtaskplanning.model.Task;
import java.time.ZonedDateTime;

public abstract class TaskFactory {

  public static Task getTask(String title, Boolean status) {
    Task task = new Task();
    task.setTitle(title);
    task.setId(200L);
    task.setDone(status);
    return task;
  }

  public static TaskRequest getTaskRequest(String title) {
    TaskRequest taskRequest = new TaskRequest();
    taskRequest.setTitle(title);
    taskRequest.setDeadline(ZonedDateTime.now().plusMonths(2));
    return taskRequest;
  }
}
