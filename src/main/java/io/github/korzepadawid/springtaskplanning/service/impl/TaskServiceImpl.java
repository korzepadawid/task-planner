package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.TaskRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskShortResponse;
import io.github.korzepadawid.springtaskplanning.model.Task;
import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.TaskNote;
import io.github.korzepadawid.springtaskplanning.repository.TaskNoteRepository;
import io.github.korzepadawid.springtaskplanning.repository.TaskRepository;
import io.github.korzepadawid.springtaskplanning.service.TaskListService;
import io.github.korzepadawid.springtaskplanning.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskListService taskListService;
  private final TaskRepository taskRepository;
  private final TaskNoteRepository taskNoteRepository;

  public TaskServiceImpl(
      TaskListService taskListService,
      TaskRepository taskRepository,
      TaskNoteRepository taskNoteRepository) {
    this.taskListService = taskListService;
    this.taskRepository = taskRepository;
    this.taskNoteRepository = taskNoteRepository;
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
}
