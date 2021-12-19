package io.github.korzepadawid.springtaskplanning.repository;

import io.github.korzepadawid.springtaskplanning.model.TaskNote;

public interface TaskNoteRepository {

  TaskNote save(TaskNote taskNote);
}
