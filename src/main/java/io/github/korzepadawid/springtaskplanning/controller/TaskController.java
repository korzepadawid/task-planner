package io.github.korzepadawid.springtaskplanning.controller;

import io.github.korzepadawid.springtaskplanning.dto.TaskCreateRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskShortResponse;
import io.github.korzepadawid.springtaskplanning.dto.TaskUpdateRequest;
import io.github.korzepadawid.springtaskplanning.security.UserPrincipal;
import io.github.korzepadawid.springtaskplanning.service.TaskService;
import javax.validation.Valid;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/api/v1/task-lists/{taskListId}/tasks")
  public TaskShortResponse saveTask(
      @PathVariable Long taskListId,
      @Valid @RequestBody TaskCreateRequest taskCreateRequest,
      @ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal) {
    return taskService.saveTask(userPrincipal.getId(), taskListId, taskCreateRequest);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/api/v1/task-lists/{taskListId}/tasks")
  public void findTasksByTaskListId(@PathVariable Long taskListId) {
    throw new NotYetImplementedException();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/api/v1/tasks/{taskId}")
  public void findTaskById(@PathVariable Long taskId) {
    throw new NotYetImplementedException();
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PatchMapping("/api/v1/tasks/{taskId}")
  public void toggleTaskById(
      @PathVariable Long taskId, @ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal) {
    taskService.toggleTaskById(userPrincipal.getId(), taskId);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/api/v1/tasks/{taskId}")
  public void updateTaskById(
      @PathVariable Long taskId,
      @Valid @RequestBody TaskUpdateRequest taskUpdateRequest,
      @AuthenticationPrincipal UserPrincipal userPrincipal) {
    taskService.updateTaskById(userPrincipal.getId(), taskId, taskUpdateRequest);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/api/v1/tasks/{taskId}")
  public void deleteTaskById(
      @PathVariable Long taskId, @ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal) {
    taskService.deleteTaskById(userPrincipal.getId(), taskId);
  }
}
