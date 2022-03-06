package io.github.korzepadawid.springtaskplanning.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import io.github.korzepadawid.springtaskplanning.service.UserService;
import io.github.korzepadawid.springtaskplanning.util.TaskFactory;
import io.github.korzepadawid.springtaskplanning.util.TaskListFactory;
import io.github.korzepadawid.springtaskplanning.util.UserFactory;
import java.time.ZonedDateTime;
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
    TaskCreateRequest taskCreateRequest = TaskFactory.getTaskRequest("title");
    taskCreateRequest.setNote(null);
    Task task = TaskFactory.getTask(taskCreateRequest.getTitle(), false);
    when(taskListService.getTaskListById(anyLong(), eq(taskList.getId()))).thenReturn(taskList);
    when(taskRepository.save(any(Task.class))).thenReturn(task);

    TaskShortResponse result = taskService.saveTask(1L, taskList.getId(), taskCreateRequest);

    assertThat(result)
        .isNotNull()
        .hasFieldOrPropertyWithValue("title", task.getTitle())
        .hasFieldOrPropertyWithValue("done", false);
  }

  @Test
  void shouldCreateTaskWithoutNoteWhenTrimmedNote() {
    TaskList taskList = TaskListFactory.getTaskList("task list");
    TaskCreateRequest taskCreateRequest = TaskFactory.getTaskRequest("title");
    taskCreateRequest.setNote("      ");
    Task task = TaskFactory.getTask(taskCreateRequest.getTitle(), false);
    when(taskListService.getTaskListById(anyLong(), eq(taskList.getId()))).thenReturn(taskList);
    when(taskRepository.save(any(Task.class))).thenReturn(task);

    TaskShortResponse result = taskService.saveTask(1L, taskList.getId(), taskCreateRequest);

    assertThat(result)
        .isNotNull()
        .hasFieldOrPropertyWithValue("title", task.getTitle())
        .hasFieldOrPropertyWithValue("done", false);
  }

  @Test
  void shouldCreateTaskWithNoteWhenNoteExists() {
    TaskList taskList = TaskListFactory.getTaskList("task list");
    TaskCreateRequest taskCreateRequest = TaskFactory.getTaskRequest("title");
    taskCreateRequest.setNote("creative note is going here");
    Task task = TaskFactory.getTask(taskCreateRequest.getTitle(), false);
    TaskNote taskNote = new TaskNote();
    taskNote.setTask(task);
    taskNote.setNote(taskCreateRequest.getNote());
    when(taskListService.getTaskListById(anyLong(), eq(taskList.getId()))).thenReturn(taskList);
    when(taskRepository.save(any(Task.class))).thenReturn(task);
    when(taskNoteRepository.save(any(TaskNote.class))).thenReturn(taskNote);

    TaskShortResponse result = taskService.saveTask(1L, taskList.getId(), taskCreateRequest);
    assertThat(result)
        .isNotNull()
        .hasFieldOrPropertyWithValue("title", task.getTitle())
        .hasFieldOrPropertyWithValue("done", false);

    verify(taskNoteRepository, times(1)).save(any(TaskNote.class));
  }

  @Test
  void shouldThrowResourceNotFoundExceptionWhenTaskDoesNotExist() {
    final Long taskId = 1L;
    User user = UserFactory.getUser();
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(taskId))).thenReturn(Optional.empty());

    Throwable exception = catchThrowable(() -> taskService.deleteTaskById(user.getId(), taskId));

    assertThat(exception).isNotNull().isInstanceOf(ResourceNotFoundException.class);
    verify(taskRepository, times(0)).delete(any(Task.class));
  }

  @Test
  void shouldDeleteTaskWhenTaskExist() {
    User user = UserFactory.getUser();
    Task task = TaskFactory.getTask("task", false);
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));

    taskService.deleteTaskById(user.getId(), task.getId());

    verify(taskRepository, times(1)).delete(any(Task.class));
  }

  @Test
  void shouldChangeTaskStatusUndoneWhenDone() {
    User user = UserFactory.getUser();
    Task task = TaskFactory.getTask("task", true);
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));

    taskService.toggleTaskById(user.getId(), task.getId());

    assertThat(task.getDone()).isFalse();
  }

  @Test
  void shouldChangeTaskStatusDoneWhenUndone() {
    User user = UserFactory.getUser();
    Task task = TaskFactory.getTask("task", false);
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));

    taskService.toggleTaskById(user.getId(), task.getId());

    assertThat(task.getDone()).isTrue();
  }

  @Test
  void shouldNotThrowAnyExceptionWhenChangesAreNull() {
    User user = UserFactory.getUser();
    Task task = TaskFactory.getTask("task", true);
    task.setTaskNote(null);
    var taskTitle = task.getTitle();
    var taskDeadline = task.getDeadline();
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));

    taskService.updateTaskById(user.getId(), task.getId(), null);

    assertThat(task.getTitle()).isEqualTo(taskTitle);
    assertThat(task.getDeadline()).isEqualTo(taskDeadline);
    assertThat(task.getTaskNote()).isNull();
  }

  @Test
  void shouldNotUpdateSinglePropertyWhenSinglePropertyIsNull() {
    User user = UserFactory.getUser();
    Task task = TaskFactory.getTask("task", true);
    TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
    taskUpdateRequest.setTitle("new title");
    task.setTaskNote(null);
    var taskDeadline = task.getDeadline();
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));

    taskService.updateTaskById(user.getId(), task.getId(), taskUpdateRequest);

    assertThat(task.getTitle()).isEqualTo(taskUpdateRequest.getTitle());
    assertThat(task.getDeadline()).isEqualTo(taskDeadline);
    assertThat(task.getTaskNote()).isNull();
  }

  @Test
  void shouldUpdateTaskNoteWhenTaskNotDoesNotExistPreviously() {
    User user = UserFactory.getUser();
    Task task = TaskFactory.getTask("task", true);
    TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
    taskUpdateRequest.setNote("x".repeat(20));
    TaskNote taskNote = new TaskNote();
    taskNote.setNote(taskUpdateRequest.getNote());
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));
    when(taskNoteRepository.save(any(TaskNote.class))).thenReturn(taskNote);

    taskService.updateTaskById(user.getId(), task.getId(), taskUpdateRequest);

    assertThat(task.getTaskNote()).isNotNull();
    assertThat(task.getTaskNote().getNote()).isEqualTo(taskUpdateRequest.getNote());
  }

  @Test
  void shouldUpdateAllPropertiesWhenChangesAreComplete() {
    User user = UserFactory.getUser();
    Task task = TaskFactory.getTask("task", true);
    task.setTaskNote(new TaskNote());
    TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
    taskUpdateRequest.setDeadline(ZonedDateTime.now().plusHours(5));
    taskUpdateRequest.setTitle("new title");
    taskUpdateRequest.setNote("x".repeat(20));
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));

    taskService.updateTaskById(user.getId(), task.getId(), taskUpdateRequest);

    assertThat(task.getTaskNote()).isNotNull();
    assertThat(task.getTaskNote().getNote()).isEqualTo(taskUpdateRequest.getNote());
    assertThat(task.getTitle()).isEqualTo(taskUpdateRequest.getTitle());
    assertThat(task.getDeadline()).isEqualTo(taskUpdateRequest.getDeadline());
  }

  @Test
  void shouldReturnTaskWhenNoteIsNull() {
    User user = UserFactory.getUser();
    Task task = TaskFactory.getTask("task", true);
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));

    TaskLongResponse result = taskService.findTaskById(user.getId(), task.getId());

    assertThat(result.getNote()).isNull();
    assertThat(result.getTitle()).isEqualTo(task.getTitle());
    assertThat(result.getDone()).isEqualTo(task.getDone());
    assertThat(result.getDeadline()).isEqualTo(task.getDeadline());
  }

  @Test
  void shouldReturnTaskWhenNoteIsNotNull() {
    User user = UserFactory.getUser();
    Task task = TaskFactory.getTask("task", true);
    TaskNote taskNote = new TaskNote();
    taskNote.setNote("blahblahblah");
    task.addNote(taskNote);
    when(userService.getUserById(user.getId())).thenReturn(user);
    when(taskRepository.findByUserAndId(any(User.class), eq(task.getId())))
        .thenReturn(Optional.of(task));

    TaskLongResponse result = taskService.findTaskById(user.getId(), task.getId());

    assertThat(result.getNote()).isNotNull();
    assertThat(result.getNote()).isEqualTo(taskNote.getNote());
    assertThat(result.getTitle()).isEqualTo(task.getTitle());
    assertThat(result.getDone()).isEqualTo(task.getDone());
    assertThat(result.getDeadline()).isEqualTo(task.getDeadline());
  }
}
