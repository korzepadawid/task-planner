package io.github.korzepadawid.springtaskplanning.repository.sql;

import io.github.korzepadawid.springtaskplanning.model.TaskNote;
import io.github.korzepadawid.springtaskplanning.repository.TaskNoteRepository;
import org.springframework.data.repository.CrudRepository;

public interface TaskNoteRepositorySql extends TaskNoteRepository, CrudRepository<TaskNote, Long> {}
