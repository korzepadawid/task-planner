package io.github.korzepadawid.springtaskplanning.repository;

import io.github.korzepadawid.springtaskplanning.model.Task;
import io.github.korzepadawid.springtaskplanning.model.User;
import java.util.Optional;

public interface TaskRepository {

  Optional<Task> findByUserAndId(User user, Long id);

  void delete(Task task);

  Task save(Task task);
}
