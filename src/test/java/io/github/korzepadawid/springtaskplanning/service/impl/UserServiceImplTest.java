package io.github.korzepadawid.springtaskplanning.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import io.github.korzepadawid.springtaskplanning.util.UserFactory;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserServiceImpl userService;

  @Test
  void shouldThrowResourceNotFoundExceptionWhenUserDoesNotExist() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    Throwable exception = catchThrowable(() -> userService.findUserByEmail("test@email.com"));

    assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void shouldReturnUserWhenUserExists() {
    User user = UserFactory.getUser(AuthProvider.GOOGLE);
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    UserResponse result = userService.findUserByEmail(user.getEmail());

    assertThat(result).isNotNull().hasFieldOrPropertyWithValue("email", user.getEmail());
  }
}
