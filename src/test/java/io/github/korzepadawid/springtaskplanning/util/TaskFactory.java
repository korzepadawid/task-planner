package io.github.korzepadawid.springtaskplanning.util;

import io.github.korzepadawid.springtaskplanning.model.Task;

public abstract class TaskFactory {

  public static Task getTask(String title, Boolean status) {
    Task task = new Task();
    task.setTitle(title);
    task.setId(200L);
    task.setDone(status);
    return task;
  }
}
