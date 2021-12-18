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
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import io.github.korzepadawid.springtaskplanning.util.TaskListFactory;
import io.github.korzepadawid.springtaskplanning.util.UserFactory;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskListServiceImplTest {

  @Mock private UserRepository userRepository;

  @Mock private TaskListRepository taskListRepository;

  @InjectMocks private TaskListServiceImpl taskListService;

  @Test
  void shouldThrowResourceNotFoundExceptionWhenUserDoesNotExist() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    Throwable exception = catchThrowable(() -> taskListService.saveTaskList(1L, null));

    assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void shouldThrowBusinessLogicExceptionWhenTaskListTitleHasBeenAlreadyTaken() {
    final String taskListTitle = "cool title";
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    TaskList taskList = TaskListFactory.getTaskList(taskListTitle);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
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
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
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
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
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
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(taskListRepository.findByUserAndId(any(User.class), eq(taskList.getId())))
        .thenReturn(Optional.of(taskList));

    TaskListResponse result = taskListService.findTaskListById(user.getId(), taskList.getId());

    assertThat(result).isNotNull().hasFieldOrPropertyWithValue("title", taskList.getTitle());
  }
}
