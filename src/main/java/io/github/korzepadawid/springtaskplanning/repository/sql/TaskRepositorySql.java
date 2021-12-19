package io.github.korzepadawid.springtaskplanning.repository.sql;

import io.github.korzepadawid.springtaskplanning.model.Task;
import io.github.korzepadawid.springtaskplanning.repository.TaskRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepositorySql extends TaskRepository, PagingAndSortingRepository<Task, Long> {}
