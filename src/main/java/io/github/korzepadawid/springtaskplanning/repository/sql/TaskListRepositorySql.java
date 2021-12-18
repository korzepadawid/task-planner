package io.github.korzepadawid.springtaskplanning.repository.sql;

import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.TaskListRepository;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskListRepositorySql
    extends TaskListRepository, PagingAndSortingRepository<TaskList, Long> {

  Optional<TaskList> findByUserAndTitle(User user, String taskListTitle);

  Optional<TaskList> findByUserAndId(User user, Long taskListId);

  Integer deleteByUserAndId(User user, Long taskListId);
}
