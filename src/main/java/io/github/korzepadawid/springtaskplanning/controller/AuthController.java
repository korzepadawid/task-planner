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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(
      @Valid @RequestBody AuthRegisterRequest authRegisterRequest) {
    return new ResponseEntity<>(authService.register(authRegisterRequest), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthLoginResponse> login(
      @Valid @RequestBody AuthLoginRequest authLoginRequest) {
    return new ResponseEntity<>(authService.login(authLoginRequest), HttpStatus.OK);
  }
}
