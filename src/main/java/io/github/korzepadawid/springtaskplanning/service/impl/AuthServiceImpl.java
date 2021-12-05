package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.AuthRegisterRequest;
import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.exception.UserAlreadyExistsException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import io.github.korzepadawid.springtaskplanning.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserResponse register(AuthRegisterRequest authRegisterRequest) {
    userRepository
        .findByEmail(authRegisterRequest.getEmail())
        .ifPresent(
            user -> {
              throw new UserAlreadyExistsException(user);
            });

    User user = new User();

    user.setName(authRegisterRequest.getName());
    user.setEmail(authRegisterRequest.getEmail());
    user.setAuthProvider(AuthProvider.LOCAL);
    user.setPassword(passwordEncoder.encode(authRegisterRequest.getPassword()));

    User savedUser = userRepository.save(user);

    return new UserResponse(savedUser);
  }
}
