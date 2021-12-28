package io.github.korzepadawid.springtaskplanning.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.github.korzepadawid.springtaskplanning.dto.AuthRegisterRequest;
import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.exception.UserAlreadyExistsException;
import io.github.korzepadawid.springtaskplanning.util.UserFactory;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private UserRepository userRepository;

  @InjectMocks private AuthServiceImpl authService;

  @Test
  void shouldThrowUserAlreadyExistsExceptionWhenUserAlreadyExists() {
    AuthRegisterRequest authRequestUser = UserFactory.getUserRegisterRequest();
    User user = UserFactory.getUser(AuthProvider.GOOGLE);
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    Throwable exception = catchThrowable(() -> authService.register(authRequestUser));

    assertThat(exception)
        .isInstanceOf(UserAlreadyExistsException.class)
        .hasMessageContaining(user.getEmail());
  }

  @Test
  void shouldRegisterNewUserWhenUserDoesNotExist() {
    AuthRegisterRequest authRequestUser = UserFactory.getUserRegisterRequest();
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    String hashedPassword = "Ha$h3dP@s5W0rD012";
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(userRepository.save(any())).thenReturn(user);
    when(passwordEncoder.encode(any())).thenReturn(hashedPassword);

    UserResponse result = authService.register(authRequestUser);

    assertThat(result).isNotNull().hasFieldOrPropertyWithValue("email", user.getEmail());
  }
}
