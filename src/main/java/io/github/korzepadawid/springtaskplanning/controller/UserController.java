package io.github.korzepadawid.springtaskplanning.controller;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.security.CurrentlyAuthenticatedUser;
import io.github.korzepadawid.springtaskplanning.security.UserPrincipal;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponse> findCurrentUser(
      @CurrentlyAuthenticatedUser UserPrincipal userPrincipal) {
    UserResponse userResponse = userService.findUserByEmail(userPrincipal.getEmail());
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }
}
