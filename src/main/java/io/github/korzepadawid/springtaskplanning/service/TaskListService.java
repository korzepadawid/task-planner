package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.TaskListRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskListResponse;
import org.springframework.data.domain.Page;

public interface TaskListService {

  TaskListResponse saveTaskList(Long userId, TaskListRequest taskListRequest);

  TaskListResponse findTaskListById(Long userId, Long taskListId);

  void deleteTaskListById(Long userId, Long taskListId);

  void updateTaskListById(Long userId, Long taskListId, TaskListRequest updates);

  Page<TaskListResponse> findAllTaskListsByUserId(Long userId, Integer pageNumber);
}
