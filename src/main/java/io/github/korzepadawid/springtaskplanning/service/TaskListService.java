package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.TaskListRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskListResponse;
import java.util.List;

public interface TaskListService {

  TaskListResponse saveTaskList(Long userId, TaskListRequest taskListRequest);

  TaskListResponse findTaskListById(Long userId, Long taskListId);

  void deleteTaskListById(Long userId, Long taskListId);

  void updateTaskListById(Long userId, Long taskListId, TaskListRequest updates);

  List<TaskListResponse> findAllTaskListsByUserId(Long userId);
}
