package io.github.korzepadawid.springtaskplanning.controller;

import io.github.korzepadawid.springtaskplanning.dto.TaskListRequest;
import io.github.korzepadawid.springtaskplanning.dto.TaskListResponse;
import io.github.korzepadawid.springtaskplanning.security.UserPrincipal;
import io.github.korzepadawid.springtaskplanning.service.TaskListService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/task-lists")
public class TaskListController {

  private final TaskListService taskListService;

  public TaskListController(TaskListService taskListService) {
    this.taskListService = taskListService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskListResponse saveTaskList(
      @Valid @RequestBody TaskListRequest taskListRequest,
      @ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal) {
    return taskListService.saveTaskList(userPrincipal.getId(), taskListRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<TaskListResponse> findAllTaskLists(
      @ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal) {
    return taskListService.findAllTaskListsByUserId(userPrincipal.getId());
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public TaskListResponse findTaskListById(
      @PathVariable Long id, @ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal) {
    return taskListService.findTaskListById(userPrincipal.getId(), id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTaskListById(
      @PathVariable Long id, @ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal) {
    taskListService.deleteTaskListById(userPrincipal.getId(), id);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateTaskListById(
      @PathVariable Long id,
      @ApiIgnore @AuthenticationPrincipal UserPrincipal userPrincipal,
      @Valid @RequestBody TaskListRequest taskListRequest) {
    taskListService.updateTaskListById(userPrincipal.getId(), id, taskListRequest);
  }
}
