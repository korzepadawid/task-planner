package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.TaskRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskShortResponse;
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
  public TaskShortResponse saveTask(Long userId, Long taskListId, TaskRequest taskRequest) {
    TaskList taskList = taskListService.findTaskListById(userId, taskListId);

    Task task = new Task();
    task.setTitle(taskRequest.getTitle());
    task.setDeadline(taskRequest.getDeadline());
    task.setDone(false);
    task.setUser(taskList.getUser());
    taskList.addTaskToList(task);

    String note = taskRequest.getNote();
    Task savedTask = taskRepository.save(task);

    if (note != null && note.trim().length() >= 1) {
      TaskNote taskNote = new TaskNote();
      taskNote.setNote(taskRequest.getNote());
      savedTask.addNote(taskNote);
      taskNoteRepository.save(taskNote);
    }

    return new TaskShortResponse(savedTask);
  }

  @Override
  @Transactional
  public void deleteTaskById(Long userId, Long taskId) {
    Task task = getTaskByUserAndId(userId, taskId);
    taskRepository.delete(task);
  }

  private Task getTaskByUserAndId(Long userId, Long id) {
    User user = userService.findUserById(userId);
    return taskRepository
        .findByUserAndId(user, id)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
  }
}
