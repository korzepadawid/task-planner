package io.github.korzepadawid.springtaskplanning.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.util.TaskFactory;
import org.junit.jupiter.api.Test;

class TaskListResponseTest {

  @Test
  void shouldReturnZeroWhenZeroTasks() {
    TaskList taskList = new TaskList();

    TaskListResponse result = new TaskListResponse(taskList);

    assertThat(result.getTotal()).isEqualTo(0);
    assertThat(result.getUndone()).isEqualTo(0);
    assertThat(result.getDone()).isEqualTo(0);
  }

  @Test
  void shouldReturnValidCountWhenTasksExist() {
    TaskList taskList = new TaskList();

    for (int i = 0; i < 4; i++) {
      taskList.addTaskToList(TaskFactory.getTask("x".repeat(i), true));
    }

    for (int i = 0; i < 3; i++) {
      taskList.addTaskToList(TaskFactory.getTask("y".repeat(i), false));
    }

    TaskListResponse result = new TaskListResponse(taskList);

    assertThat(result.getTotal()).isEqualTo(7);
    assertThat(result.getUndone()).isEqualTo(3);
    assertThat(result.getDone()).isEqualTo(4);
  }
}
