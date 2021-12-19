package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.TaskListRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskListResponse;
import io.github.korzepadawid.springtaskplanning.model.TaskList;
import java.util.List;

public interface TaskListService {

  TaskListResponse saveTaskList(Long userId, TaskListRequest taskListRequest);

  TaskList findTaskListById(Long userId, Long taskListId);

  void deleteTaskListById(Long userId, Long taskListId);

  void updateTaskListById(Long userId, Long taskListId, TaskListRequest updates);

  List<TaskListResponse> findAllTaskListsByUserId(Long userId);
}
