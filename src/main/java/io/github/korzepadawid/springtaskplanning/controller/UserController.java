package io.github.korzepadawid.springtaskplanning.controller;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.security.CurrentlyAuthenticatedUser;
import io.github.korzepadawid.springtaskplanning.security.JwtAuthenticationFilter;
import io.github.korzepadawid.springtaskplanning.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  @GetMapping("/me")
  public ResponseEntity<UserResponse> findCurrentUser(
      @CurrentlyAuthenticatedUser UserPrincipal userPrincipal) {
    log.warn(userPrincipal.getEmail());
    return null;
  }
}
