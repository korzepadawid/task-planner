package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.TaskCreateRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskLongResponse;
import io.github.korzepadawid.springtaskplanning.dto.TaskShortResponse;
import io.github.korzepadawid.springtaskplanning.dto.TaskUpdateRequest;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.Task;
import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.TaskNote;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.TaskNoteRepository;
import io.github.korzepadawid.springtaskplanning.repository.TaskRepository;
import io.github.korzepadawid.springtaskplanning.service.TaskListService;
import io.github.korzepadawid.springtaskplanning.service.TaskService;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskListService taskListService;
  private final TaskRepository taskRepository;
  private final TaskNoteRepository taskNoteRepository;
  private final UserService userService;

  public TaskServiceImpl(
      TaskListService taskListService,
      TaskRepository taskRepository,
      TaskNoteRepository taskNoteRepository,
      UserService userService) {
    this.taskListService = taskListService;
    this.taskRepository = taskRepository;
    this.taskNoteRepository = taskNoteRepository;
    this.userService = userService;
  }

  @Override
  @Transactional
  public TaskShortResponse saveTask(
      Long userId, Long taskListId, TaskCreateRequest taskCreateRequest) {
    TaskList taskList = taskListService.findTaskListById(userId, taskListId);

    Task task = new Task();
    task.setTitle(taskCreateRequest.getTitle());
    task.setDeadline(taskCreateRequest.getDeadline());
    task.setDone(false);
    task.setUser(taskList.getUser());
    taskList.addTaskToList(task);

    String note = taskCreateRequest.getNote();
    Task savedTask = taskRepository.save(task);

    if (note != null && note.trim().length() >= 1) {
      TaskNote taskNote = new TaskNote();
      taskNote.setNote(taskCreateRequest.getNote());
      savedTask.addNote(taskNote);
      taskNoteRepository.save(taskNote);
    }

    return new TaskShortResponse(savedTask);
  }

  @Override
  @Transactional(readOnly = true)
  public TaskLongResponse findTaskById(Long userId, Long taskListId) {
    Task task = getTaskByUserAndId(userId, taskListId);
    return new TaskLongResponse(task);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TaskShortResponse> findAllTasksByUserIdAndTaskListId(
      Long userId, Long taskListId, Integer page) {
    TaskList taskList = taskListService.findTaskListById(userId, taskListId);
    return taskRepository
        .findAllByTaskList(taskList, PageRequest.of(Math.max(0, page - 1), 5))
        .map(TaskShortResponse::new);
  }

  @Override
  @Transactional
  public void updateTaskById(Long userId, Long taskId, TaskUpdateRequest taskUpdateRequest) {
    Task task = getTaskByUserAndId(userId, taskId);
    if (taskUpdateRequest != null) {
      if (taskUpdateRequest.getDeadline() != null) {
        task.setDeadline(taskUpdateRequest.getDeadline());
      }
      if (taskUpdateRequest.getTitle() != null) {
        task.setTitle(taskUpdateRequest.getTitle());
      }
      if (taskUpdateRequest.getNote() != null) {
        if (task.getTaskNote() != null) {
          task.getTaskNote().setNote(taskUpdateRequest.getNote());
        } else {
          TaskNote taskNote = new TaskNote();
          taskNote.setNote(taskUpdateRequest.getNote());
          task.addNote(taskNote);
          taskNoteRepository.save(taskNote);
        }
      }
    }
  }

  @Override
  @Transactional
  public void deleteTaskById(Long userId, Long taskId) {
    Task task = getTaskByUserAndId(userId, taskId);
    taskRepository.delete(task);
  }

  @Override
  @Transactional
  public void toggleTaskById(Long userId, Long taskId) {
    Task task = getTaskByUserAndId(userId, taskId);
    task.setDone(!task.getDone());
  }

  private Task getTaskByUserAndId(Long userId, Long id) {
    User user = userService.findUserById(userId);
    return taskRepository
        .findByUserAndId(user, id)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
  }
}
