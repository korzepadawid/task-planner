package io.github.korzepadawid.springtaskplanning.exception;

import io.github.korzepadawid.springtaskplanning.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User already exists.")
public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(User user) {
    super(String.format("User with email %s already exists.", user.getEmail()));
  }
}
