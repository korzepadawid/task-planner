package io.github.korzepadawid.springtaskplanning.controller;

import io.github.korzepadawid.springtaskplanning.dto.AuthLoginRequest;
import io.github.korzepadawid.springtaskplanning.dto.AuthLoginResponse;
import io.github.korzepadawid.springtaskplanning.dto.AuthRegisterRequest;
import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.service.AuthService;
import javax.validation.Valid;
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
  public ResponseEntity<AuthLoginResponse> login(
      @Valid @RequestBody AuthLoginRequest authLoginRequest) {
    return new ResponseEntity<>(authService.login(authLoginRequest), HttpStatus.OK);
  }
}
