package io.github.korzepadawid.springtaskplanning.repository.sql;

import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositorySql extends UserRepository, CrudRepository<User, Long> {

  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);
}
