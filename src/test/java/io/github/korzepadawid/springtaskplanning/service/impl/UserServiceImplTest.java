package io.github.korzepadawid.springtaskplanning.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.korzepadawid.springtaskplanning.exception.BusinessLogicException;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.Avatar;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.AvatarRepository;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import io.github.korzepadawid.springtaskplanning.service.StorageService;
import io.github.korzepadawid.springtaskplanning.util.AvatarFactory;
import io.github.korzepadawid.springtaskplanning.util.UserFactory;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private AvatarRepository avatarRepository;
  @Mock private StorageService storageService;

  @InjectMocks private UserServiceImpl userService;

  @Test
  void shouldThrowResourceNotFoundExceptionWhenUserDoesNotExist() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    Throwable exception = catchThrowable(() -> userService.getUserById(1L));

    assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void shouldReturnUserWhenUserExists() {
    User user = UserFactory.getUser(AuthProvider.GOOGLE);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    User result = userService.getUserById(user.getId());

    assertThat(result).isNotNull().hasFieldOrPropertyWithValue("id", user.getId());
  }

  @Test
  void shouldThrowResourceNotFoundExceptionWhenUserDoesNotExistAndStopUpdatingAvatar() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    Throwable exception = catchThrowable(() -> userService.saveOrUpdateAvatarByUserId(1L, null));

    assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void shouldThrowBusinessLogicExceptionWhenUserUpdatesAvatarAndComesFromThirdPartyAuthProvider() {
    User user = UserFactory.getUser(AuthProvider.GOOGLE);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    Throwable exception = catchThrowable(() -> userService.saveOrUpdateAvatarByUserId(user.getId(), null));

    assertThat(exception).isInstanceOf(BusinessLogicException.class);
  }

  @Test
  void shouldCreateNewAvatarWhenAvatarDoesNotExist() {
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    user.setAvatar(null);
    Avatar avatar = AvatarFactory.getAvatar();
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(avatarRepository.save(any())).thenReturn(avatar);

    userService.saveOrUpdateAvatarByUserId(user.getId(), null);

    verify(userRepository, times(1)).findById(anyLong());
    verify(avatarRepository, times(1)).save(any());
  }

  @Test
  void shouldNotCreateNewAvatarWhenAvatarExists() {
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    Avatar avatar = AvatarFactory.getAvatar();
    user.setAvatar(avatar);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    userService.saveOrUpdateAvatarByUserId(user.getId(), null);

    verify(userRepository, times(1)).findById(anyLong());
    verify(avatarRepository, times(0)).save(any());
  }

  @Test
  void shouldThrowResourceNotFoundExceptionWhenUserDoesNotExistAndStopLookingForAvatar() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    Throwable exception = catchThrowable(() -> userService.findAvatarByUserId(1L));

    assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void shouldThrowResourceNotFoundExceptionWhenAvatarDoesNotExist() {
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    user.setAvatar(null);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    Throwable exception = catchThrowable(() -> userService.findAvatarByUserId(1L));

    assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void shouldReturnPhotoInBytesWhenAvatarExists() throws IOException {
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    Avatar avatar = AvatarFactory.getAvatar();
    user.setAvatar(avatar);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(storageService.downloadFileByStorageKey(anyString())).thenReturn(new byte[10]);

    byte[] result = userService.findAvatarByUserId(1L);

    assertThat(result).hasSize(10);
  }
}
