package io.github.korzepadawid.springtaskplanning.controller;

import io.github.korzepadawid.springtaskplanning.dto.AuthLoginRequest;
import io.github.korzepadawid.springtaskplanning.dto.AuthLoginResponse;
import io.github.korzepadawid.springtaskplanning.dto.AuthRegisterRequest;
import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.service.AuthService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/register")
  public UserResponse register(@Valid @RequestBody AuthRegisterRequest authRegisterRequest) {
    return authService.register(authRegisterRequest);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/login")
  public AuthLoginResponse login(@Valid @RequestBody AuthLoginRequest authLoginRequest) {
    return authService.login(authLoginRequest);
  }
}
