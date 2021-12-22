package io.github.korzepadawid.springtaskplanning.repository;

import io.github.korzepadawid.springtaskplanning.model.Task;
import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepository {

  Optional<Task> findByUserAndId(User user, Long id);

  void delete(Task task);

  Page<Task> findAllByTaskList(TaskList taskList, Pageable pageable);

  Task save(Task task);
}
