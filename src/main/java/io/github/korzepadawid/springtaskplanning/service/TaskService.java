package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.TaskCreateRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskLongResponse;
import io.github.korzepadawid.springtaskplanning.dto.TaskShortResponse;
import io.github.korzepadawid.springtaskplanning.dto.TaskUpdateRequest;
import org.springframework.data.domain.Page;

public interface TaskService {

  TaskShortResponse saveTask(Long userId, Long taskListId, TaskCreateRequest taskCreateRequest);

  TaskLongResponse findTaskById(Long userId, Long taskListId);

  Page<TaskShortResponse> findAllTasksByUserIdAndTaskListId(
      Long userId, Long taskListId, Integer page);

  void updateTaskById(Long userId, Long taskId, TaskUpdateRequest taskUpdateRequest);

  void deleteTaskById(Long userId, Long taskId);

  void toggleTaskById(Long userId, Long taskId);
}
