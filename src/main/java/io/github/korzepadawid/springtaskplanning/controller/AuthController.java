package io.github.korzepadawid.springtaskplanning.controller;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @PostMapping("/api/v1/auth/register")
  public ResponseEntity<?> register() {
    throw new NotYetImplementedException();
  }

  @PostMapping("/api/v1/auth/login")
  public ResponseEntity<?> login() {
    throw new NotYetImplementedException();
  }
}
