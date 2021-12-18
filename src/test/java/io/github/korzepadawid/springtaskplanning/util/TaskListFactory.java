package io.github.korzepadawid.springtaskplanning.util;

import io.github.korzepadawid.springtaskplanning.dto.TaskListRequest;
import io.github.korzepadawid.springtaskplanning.model.TaskList;

public abstract class TaskListFactory {

  public static TaskListRequest getTaskListRequest(String title) {
    TaskListRequest taskListRequest = new TaskListRequest();
    taskListRequest.setTitle(title);
    return taskListRequest;
  }

  public static TaskList getTaskList(String title) {
    TaskList taskList = new TaskList();
    taskList.setId(1L);
    taskList.setTitle(title);
    return taskList;
  }
}
