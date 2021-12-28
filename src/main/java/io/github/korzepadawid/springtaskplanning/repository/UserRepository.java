package io.github.korzepadawid.springtaskplanning.repository;

import io.github.korzepadawid.springtaskplanning.model.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findByEmail(String email);

  Optional<User> findById(Long id);

  User save(User user);
}
