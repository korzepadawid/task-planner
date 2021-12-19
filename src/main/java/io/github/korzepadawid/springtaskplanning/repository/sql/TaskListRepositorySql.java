package io.github.korzepadawid.springtaskplanning.repository.sql;

import io.github.korzepadawid.springtaskplanning.model.TaskList;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.TaskListRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskListRepositorySql
    extends TaskListRepository, PagingAndSortingRepository<TaskList, Long> {

  Optional<TaskList> findByUserAndTitle(User user, String taskListTitle);

  Optional<TaskList> findByUserAndId(User user, Long taskListId);

  @Query(
      "select tl from TaskList tl left join fetch tl.tasks t where tl.user = :user order by tl"
          + ".dateAudit.createdAt desc ")
  List<TaskList> findAllByUser(User user);

  Integer deleteByUserAndId(User user, Long taskListId);
}
