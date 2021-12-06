package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.AuthLoginRequest;
import io.github.korzepadawid.springtaskplanning.dto.AuthLoginResponse;
import io.github.korzepadawid.springtaskplanning.dto.AuthRegisterRequest;
import io.github.korzepadawid.springtaskplanning.dto.UserResponse;

public interface AuthService {

  UserResponse register(AuthRegisterRequest authRegisterRequest);

  AuthLoginResponse login(AuthLoginRequest authLoginRequest);
}
