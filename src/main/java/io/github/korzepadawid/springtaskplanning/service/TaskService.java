package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.TaskRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskShortResponse;

public interface TaskService {

  TaskShortResponse saveTask(Long userId, Long taskListId, TaskRequest taskRequest);

  void deleteTaskById(Long userId, Long taskId);
}
