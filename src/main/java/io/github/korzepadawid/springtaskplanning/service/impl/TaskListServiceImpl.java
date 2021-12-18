package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.TaskListRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskListResponse;
import io.github.korzepadawid.springtaskplanning.exception.BusinessLogicException;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.repository.TaskListRepository;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import io.github.korzepadawid.springtaskplanning.service.TaskListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskListServiceImpl implements TaskListService {

  private final TaskListRepository taskListRepository;
  private final UserRepository userRepository;

  public TaskListServiceImpl(TaskListRepository taskListRepository, UserRepository userRepository) {
    this.taskListRepository = taskListRepository;
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public TaskListResponse saveTaskList(Long userId, TaskListRequest taskListRequest) {
    return userRepository
        .findById(userId)
        .map(
            user -> {
              taskListRepository
                  .findByUserAndTitle(user, taskListRequest.getTitle())
                  .ifPresent(
                      taskList -> {
                        throw new BusinessLogicException("Title has been already taken.");
                      });
              TaskList taskList = mapRequestToEntity(taskListRequest);
              user.addTaskList(taskList);
              TaskList savedTaskList = taskListRepository.save(taskList);
              return new TaskListResponse(savedTaskList);
            })
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public TaskListResponse findTaskListById(Long userId, Long taskListId) {
    return userRepository
        .findById(userId)
        .map(
            user ->
                taskListRepository
                    .findByUserAndId(user, taskListId)
                    .map(TaskListResponse::new)
                    .orElseThrow(() -> new ResourceNotFoundException("Task list not found")))
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  private TaskList mapRequestToEntity(TaskListRequest taskListRequest) {
    TaskList taskList = new TaskList();
    taskList.setTitle(taskListRequest.getTitle());
    return taskList;
  }
}
