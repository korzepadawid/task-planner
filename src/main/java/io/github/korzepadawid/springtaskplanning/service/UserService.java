package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;

public interface UserService {

  UserResponse findUserByEmail(String email);
}
