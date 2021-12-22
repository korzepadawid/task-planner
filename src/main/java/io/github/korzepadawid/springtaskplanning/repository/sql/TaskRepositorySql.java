package io.github.korzepadawid.springtaskplanning.repository.sql;

import io.github.korzepadawid.springtaskplanning.model.Task;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.TaskRepository;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepositorySql extends TaskRepository, PagingAndSortingRepository<Task, Long> {

  Optional<Task> findByUserAndId(User user, Long id);
}
