package io.github.korzepadawid.springtaskplanning.repository;

import io.github.korzepadawid.springtaskplanning.model.Task;

public interface TaskRepository {

  Task save(Task task);
}
