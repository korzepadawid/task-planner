package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.AuthLoginRequest;
import io.github.korzepadawid.springtaskplanning.dto.AuthLoginResponse;
import io.github.korzepadawid.springtaskplanning.dto.AuthRegisterRequest;
import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.exception.UserAlreadyExistsException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import io.github.korzepadawid.springtaskplanning.security.JwtProvider;
import io.github.korzepadawid.springtaskplanning.security.UserPrincipal;
import io.github.korzepadawid.springtaskplanning.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;

  public AuthServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtProvider jwtProvider) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtProvider = jwtProvider;
  }

  @Override
  @Transactional
  public UserResponse register(AuthRegisterRequest authRegisterRequest) {
    userRepository
        .findByEmail(authRegisterRequest.getEmail())
        .ifPresent(
            user -> {
              throw new UserAlreadyExistsException(user);
            });
    User user = mapRegisterRequestToEntity(authRegisterRequest);
    User savedUser = userRepository.save(user);
    return new UserResponse(savedUser);
  }

  @Override
  @Transactional(readOnly = true)
  public AuthLoginResponse login(AuthLoginRequest authLoginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authLoginRequest.getEmail(), authLoginRequest.getPassword()));

    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateToken(userPrincipal);
    return new AuthLoginResponse(jwt);
  }

  private User mapRegisterRequestToEntity(AuthRegisterRequest authRegisterRequest) {
    User user = new User();
    user.setName(authRegisterRequest.getName());
    user.setEmail(authRegisterRequest.getEmail());
    user.setPassword(passwordEncoder.encode(authRegisterRequest.getPassword()));
    return user;
  }
}
