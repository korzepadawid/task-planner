package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.TaskListRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskListResponse;

public interface TaskListService {

  TaskListResponse saveTaskList(Long userId, TaskListRequest taskListRequest);

  TaskListResponse findTaskListById(Long userId, Long taskListId);
}
