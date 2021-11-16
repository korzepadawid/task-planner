package io.github.korzepadawid.springtaskplanning.security;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.github.korzepadawid.springtaskplanning.factory.UserDataFactory;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private CustomUserDetailsService customUserDetailsService;

  @Test
  void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    var exception =
        catchThrowable(() -> customUserDetailsService.loadUserByUsername("some@email.com"));

    assertThat(exception).isInstanceOf(UsernameNotFoundException.class);
  }

  @Test
  void shouldReturnUserPrincipalWhenUserExists() {
    var user = UserDataFactory.getUser(AuthProvider.LOCAL);
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    var userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

    assertThat(userDetails).isNotNull().hasFieldOrPropertyWithValue("email", user.getEmail());
  }
}
