package io.github.korzepadawid.springtaskplanning.repository.sql;

import io.github.korzepadawid.springtaskplanning.model.Avatar;
import io.github.korzepadawid.springtaskplanning.repository.AvatarRepository;
import org.springframework.data.repository.CrudRepository;

public interface AvatarRepositorySql extends AvatarRepository, CrudRepository<Avatar, Long> {}
