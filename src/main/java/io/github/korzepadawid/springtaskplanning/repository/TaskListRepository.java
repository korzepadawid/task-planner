package io.github.korzepadawid.springtaskplanning.repository;

import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskListRepository {

  TaskList save(TaskList taskList);

  Optional<TaskList> findByUserAndTitle(User user, String taskListTitle);

  Optional<TaskList> findByUserAndId(User user, Long taskListId);

  Integer deleteByUserAndId(User user, Long taskListId);

  Page<TaskList> findAllByUser(User user, Pageable pageable);
}
