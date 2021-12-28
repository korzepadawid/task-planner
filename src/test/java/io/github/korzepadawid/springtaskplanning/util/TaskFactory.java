package io.github.korzepadawid.springtaskplanning.util;

import io.github.korzepadawid.springtaskplanning.dto.TaskCreateRequest;
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

  public static TaskCreateRequest getTaskRequest(String title) {
    TaskCreateRequest taskCreateRequest = new TaskCreateRequest();
    taskCreateRequest.setTitle(title);
    taskCreateRequest.setDeadline(ZonedDateTime.now().plusMonths(2));
    return taskCreateRequest;
  }
}
