package io.github.korzepadawid.springtaskplanning.exception;

import io.github.korzepadawid.springtaskplanning.model.User;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(User user) {
    super(String.format("User with email %s already exists.", user.getEmail()));
  }
}
