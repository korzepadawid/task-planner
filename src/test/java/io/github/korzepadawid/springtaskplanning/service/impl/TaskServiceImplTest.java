package io.github.korzepadawid.springtaskplanning.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.korzepadawid.springtaskplanning.dto.TaskRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskShortResponse;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.Task;
import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.TaskNote;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.TaskNoteRepository;
import io.github.korzepadawid.springtaskplanning.repository.TaskRepository;
import io.github.korzepadawid.springtaskplanning.service.TaskListService;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import io.github.korzepadawid.springtaskplanning.util.TaskFactory;
import io.github.korzepadawid.springtaskplanning.util.TaskListFactory;
import io.github.korzepadawid.springtaskplanning.util.UserFactory;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

  @Mock private TaskRepository taskRepository;

  @Mock private TaskListService taskListService;

  @Mock private UserService userService;

  @Mock private TaskNoteRepository taskNoteRepository;

  @InjectMocks private TaskServiceImpl taskService;

  @Test
  void shouldCreateTaskWithoutNoteWhenNoteDoesNotExist() {
    TaskList taskList = TaskListFactory.getTaskList("task list");
    TaskRequest taskRequest = TaskFactory.getTaskRequest("title");
    taskRequest.setNote(null);
    Task task = TaskFactory.getTask(taskRequest.getTitle(), false);
    when(taskListService.findTaskListById(anyLong(), eq(taskList.getId()))).thenReturn(taskList);
    when(taskRepository.save(any(Task.class))).thenReturn(task);

    TaskShortResponse result = taskService.saveTask(1L, taskList.getId(), taskRequest);

    assertThat(result)
        .isNotNull()
        .hasFieldOrPropertyWithValue("title", task.getTitle())
        .hasFieldOrPropertyWithValue("done", false);
  }

  @Test
  void shouldCreateTaskWithoutNoteWhenTrimmedNote() {
    TaskList taskList = TaskListFactory.getTaskList("task list");
    TaskRequest taskRequest = TaskFactory.getTaskRequest("title");
    taskRequest.setNote("      ");
    Task task = TaskFactory.getTask(taskRequest.getTitle(), false);
    when(taskListService.findTaskListById(anyLong(), eq(taskList.getId()))).thenReturn(taskList);
    when(taskRepository.save(any(Task.class))).thenReturn(task);

    TaskShortResponse result = taskService.saveTask(1L, taskList.getId(), taskRequest);

    assertThat(result)
        .isNotNull()
        .hasFieldOrPropertyWithValue("title", task.getTitle())
        .hasFieldOrPropertyWithValue("done", false);
  }

  @Test
  void shouldCreateTaskWithNoteWhenNoteExists() {
    TaskList taskList = TaskListFactory.getTaskList("task list");
    TaskRequest taskRequest = TaskFactory.getTaskRequest("title");
    taskRequest.setNote("creative note is going here");
    Task task = TaskFactory.getTask(taskRequest.getTitle(), false);
    TaskNote taskNote = new TaskNote();
    taskNote.setTask(task);
    taskNote.setNote(taskRequest.getNote());
    when(taskListService.findTaskListById(anyLong(), eq(taskList.getId()))).thenReturn(taskList);
    when(taskRepository.save(any(Task.class))).thenReturn(task);
    when(taskNoteRepository.save(any(TaskNote.class))).thenReturn(taskNote);

    TaskShortResponse result = taskService.saveTask(1L, taskList.getId(), taskRequest);
    assertThat(result)
        .isNotNull()
        .hasFieldOrPropertyWithValue("title", task.getTitle())
        .hasFieldOrPropertyWithValue("done", false);

    verify(taskNoteRepository, times(1)).save(any(TaskNote.class));
  }

  @Test
  void shouldThrowResourceNotFoundExceptionWhenTaskDoesNotExist() {
    final Long taskId = 1L;
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    when(userService.findUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(taskId))).thenReturn(Optional.empty());

    Throwable exception = catchThrowable(() -> taskService.deleteTaskById(user.getId(), taskId));

    assertThat(exception).isNotNull().isInstanceOf(ResourceNotFoundException.class);
    verify(taskRepository, times(0)).delete(any(Task.class));
  }

  @Test
  void shouldDeleteTaskWhenTaskExist() {
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    Task task = TaskFactory.getTask("task", false);
    when(userService.findUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));

    taskService.deleteTaskById(user.getId(), task.getId());

    verify(taskRepository, times(1)).delete(any(Task.class));
  }
}
