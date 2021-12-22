package io.github.korzepadawid.springtaskplanning.repository.sql;

import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.TaskListRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TaskListRepositorySql extends TaskListRepository, CrudRepository<TaskList, Long> {

  Optional<TaskList> findByUserAndTitle(User user, String taskListTitle);

  Optional<TaskList> findByUserAndId(User user, Long taskListId);

  @Query(
      "select tl from TaskList tl join fetch tl.user u where u = :user order by tl.dateAudit"
          + ".createdAt desc")
  List<TaskList> findAllByUser(User user);

  Integer deleteByUserAndId(User user, Long taskListId);
}
