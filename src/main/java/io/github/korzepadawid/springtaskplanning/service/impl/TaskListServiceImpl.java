package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.TaskListRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskListResponse;
import io.github.korzepadawid.springtaskplanning.exception.BusinessLogicException;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.TaskListRepository;
import io.github.korzepadawid.springtaskplanning.service.TaskListService;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskListServiceImpl implements TaskListService {

  private final TaskListRepository taskListRepository;
  private final UserService userService;

  public TaskListServiceImpl(TaskListRepository taskListRepository, UserService userService) {
    this.taskListRepository = taskListRepository;
    this.userService = userService;
  }

  @Override
  @Transactional
  public TaskListResponse saveTaskList(Long userId, TaskListRequest taskListRequest) {
    User user = userService.findUserById(userId);
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
  }

  @Override
  @Transactional(readOnly = true)
  public TaskListResponse findTaskListById(Long userId, Long taskListId) {
    User user = userService.findUserById(userId);
    return taskListRepository
        .findByUserAndId(user, taskListId)
        .map(TaskListResponse::new)
        .orElseThrow(() -> new ResourceNotFoundException("Task list not found"));
  }

  @Override
  @Transactional
  public void deleteTaskListById(Long userId, Long taskListId) {
    User user = userService.findUserById(userId);
    if (taskListRepository.deleteByUserAndId(user, taskListId) == 0) {
      throw new ResourceNotFoundException("Task list not found");
    }
  }

  @Override
  @Transactional
  public void updateTaskListById(Long userId, Long taskListId, TaskListRequest updates) {
    if (updates != null) {
      User user = userService.findUserById(userId);
      taskListRepository
          .findByUserAndId(user, taskListId)
          .ifPresentOrElse(
              taskList -> taskList.setTitle(updates.getTitle()),
              () -> {
                throw new ResourceNotFoundException("Task list not found");
              });
    }
  }

  @Override
  @Transactional
  public Page<TaskListResponse> findAllTaskListsByUserId(Long userId, Integer pageNumber) {
    User user = userService.findUserById(userId);
    return taskListRepository
        .findAllByUser(user, PageRequest.of(Math.max(pageNumber - 1, 0), 10))
        .map(TaskListResponse::new);
  }

  private TaskList mapRequestToEntity(TaskListRequest taskListRequest) {
    TaskList taskList = new TaskList();
    taskList.setTitle(taskListRequest.getTitle());
    return taskList;
  }
}
