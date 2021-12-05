package io.github.korzepadawid.springtaskplanning.controller;

import io.github.korzepadawid.springtaskplanning.dto.AuthRegisterRequest;
import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.service.AuthService;
import javax.validation.Valid;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/api/v1/auth/register")
  public ResponseEntity<UserResponse> register(
      @Valid @RequestBody AuthRegisterRequest authRegisterRequest) {
    return new ResponseEntity<>(authService.register(authRegisterRequest), HttpStatus.CREATED);
  }

  @PostMapping("/api/v1/auth/login")
  public ResponseEntity<?> login() {
    throw new NotYetImplementedException();
  }
}
