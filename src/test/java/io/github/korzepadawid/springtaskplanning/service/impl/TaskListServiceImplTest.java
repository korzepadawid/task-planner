package io.github.korzepadawid.springtaskplanning.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import io.github.korzepadawid.springtaskplanning.dto.TaskListResponse;
import io.github.korzepadawid.springtaskplanning.exception.BusinessLogicException;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.TaskListRepository;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import io.github.korzepadawid.springtaskplanning.util.TaskListFactory;
import io.github.korzepadawid.springtaskplanning.util.UserFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskListServiceImplTest {

  @Mock private UserService userService;

  @Mock private TaskListRepository taskListRepository;

  @InjectMocks private TaskListServiceImpl taskListService;

  @Test
  void shouldThrowBusinessLogicExceptionWhenTaskListTitleHasBeenAlreadyTaken() {
    final String taskListTitle = "cool title";
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    TaskList taskList = TaskListFactory.getTaskList(taskListTitle);
    when(userService.findUserById(anyLong())).thenReturn(user);
    when(taskListRepository.findByUserAndTitle(any(User.class), eq(taskListTitle)))
        .thenReturn(Optional.of(taskList));

    Throwable exception =
        catchThrowable(
            () ->
                taskListService.saveTaskList(
                    user.getId(), TaskListFactory.getTaskListRequest(taskListTitle)));

    assertThat(exception).isNotNull().isInstanceOf(BusinessLogicException.class);
  }

  @Test
  void shouldSaveTaskListWhenTaskListTitleHasNotBeenTakenYet() {
    final String taskListTitle = "cool title";
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    TaskList taskList = TaskListFactory.getTaskList(taskListTitle);
    when(userService.findUserById(anyLong())).thenReturn(user);
    when(taskListRepository.findByUserAndTitle(any(User.class), eq(taskListTitle)))
        .thenReturn(Optional.empty());
    when(taskListRepository.save(any(TaskList.class))).thenReturn(taskList);

    TaskListResponse result =
        taskListService.saveTaskList(
            user.getId(), TaskListFactory.getTaskListRequest(taskListTitle));

    assertThat(user.getTaskLists().size()).isEqualTo(1);

    assertThat(result)
        .isNotNull()
        .hasFieldOrPropertyWithValue("title", taskListTitle)
        .hasFieldOrPropertyWithValue("total", 0)
        .hasFieldOrPropertyWithValue("undone", 0)
        .hasFieldOrPropertyWithValue("done", 0);
  }

  @Test
  void shouldThrowResourceNotFoundExceptionWhenTaskListDoesNotExist() {
    final Long taskListId = 2L;
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    when(userService.findUserById(anyLong())).thenReturn(user);
    when(taskListRepository.findByUserAndId(any(User.class), eq(taskListId)))
        .thenReturn(Optional.empty());

    Throwable exception =
        catchThrowable(() -> taskListService.findTaskListById(user.getId(), taskListId));

    assertThat(exception).isNotNull().isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void shouldReturnTaskListWhenExists() {
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    TaskList taskList = TaskListFactory.getTaskList("ok");
    when(userService.findUserById(anyLong())).thenReturn(user);
    when(taskListRepository.findByUserAndId(any(User.class), eq(taskList.getId())))
        .thenReturn(Optional.of(taskList));

    TaskList result = taskListService.findTaskListById(user.getId(), taskList.getId());

    assertThat(result).isNotNull().hasFieldOrPropertyWithValue("title", taskList.getTitle());
  }

  @Test
  void shouldThrowResourceNotFoundExceptionWhenCanNotDelete() {
    final Long taskListId = 2L;
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    when(userService.findUserById(anyLong())).thenReturn(user);
    when(taskListRepository.deleteByUserAndId(any(User.class), eq(taskListId))).thenReturn(0);

    Throwable exception =
        catchThrowable(() -> taskListService.deleteTaskListById(user.getId(), taskListId));

    assertThat(exception).isNotNull().isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void shouldThrowResourceNotFoundExceptionWhenCanNotUpdate() {
    final Long taskListId = 2L;
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    when(userService.findUserById(anyLong())).thenReturn(user);
    when(taskListRepository.findByUserAndId(any(User.class), anyLong()))
        .thenReturn(Optional.empty());

    Throwable exception =
        catchThrowable(
            () ->
                taskListService.updateTaskListById(
                    user.getId(), taskListId, TaskListFactory.getTaskListRequest("test")));

    assertThat(exception).isNotNull().isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void shouldUpdateWhenTaskListExists() {
    final Long taskListId = 2L;
    final String newTaskListTitle = "new task list title";
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    TaskList taskList = TaskListFactory.getTaskList("task list");
    when(userService.findUserById(anyLong())).thenReturn(user);
    when(taskListRepository.findByUserAndId(any(User.class), anyLong()))
        .thenReturn(Optional.of(taskList));

    taskListService.updateTaskListById(
        user.getId(), taskListId, TaskListFactory.getTaskListRequest(newTaskListTitle));

    assertThat(taskList.getTitle()).isEqualTo(newTaskListTitle);
  }

  @Test
  void shouldReturnEmptyResultWhenTaskListsDoNotExist() {
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    when(userService.findUserById(anyLong())).thenReturn(user);
    when(taskListRepository.findAllByUser(any(User.class))).thenReturn(Collections.emptyList());

    List<TaskListResponse> result = taskListService.findAllTaskListsByUserId(user.getId());

    assertThat(result.size()).isEqualTo(0);
  }

  @Test
  void shouldReturnTaskListsWhenTheyExist() {
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    List<TaskList> taskLists =
        Arrays.asList(
            TaskListFactory.getTaskList("a"),
            TaskListFactory.getTaskList("b"),
            TaskListFactory.getTaskList("c"),
            TaskListFactory.getTaskList("d"));
    when(userService.findUserById(anyLong())).thenReturn(user);
    when(taskListRepository.findAllByUser(any(User.class))).thenReturn(taskLists);

    List<TaskListResponse> result = taskListService.findAllTaskListsByUserId(user.getId());

    assertThat(result.size()).isEqualTo(taskLists.size());
  }
}
