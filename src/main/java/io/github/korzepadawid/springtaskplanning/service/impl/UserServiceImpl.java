package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.exception.BusinessLogicException;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.Avatar;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.AvatarRepository;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import io.github.korzepadawid.springtaskplanning.service.StorageService;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AvatarRepository avatarRepository;
  private final StorageService storageService;

  public UserServiceImpl(
      UserRepository userRepository,
      AvatarRepository avatarRepository,
      StorageService storageService) {
    this.userRepository = userRepository;
    this.avatarRepository = avatarRepository;
    this.storageService = storageService;
  }

  @Override
  public User getUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found."));
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse findUserById(Long id) {
    return new UserResponse(getUserById(id));
  }

  @Override
  @Transactional(readOnly = true)
  public byte[] findAvatarByUserId(Long id) {
    User user = getUserById(id);
    if (user.getAvatar() != null) {
      try {
        return storageService.downloadFile(user.getAvatar().getStorageKey());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    throw new ResourceNotFoundException("Avatar not found");
  }

  /**
   * Changing the avatar feature is impossible for OAuth2 users. It checks if the avatar already
   * exists, otherwise, it creates a new one.
   */
  @Override
  @Transactional
  public void setAvatar(Long id, MultipartFile file) {
    User user = getUserById(id);
    if (user.getAuthProvider().equals(AuthProvider.LOCAL)) {
      Avatar avatarToSave;
      if (user.getAvatar() != null) {
        avatarToSave = user.getAvatar();
      } else {
        Avatar avatar = new Avatar();
        avatar.setStorageKey(UUID.randomUUID().toString());
        avatar.setUser(user);
        avatarToSave = avatarRepository.save(avatar);
      }
      try {
        storageService.putFile(
            file, Arrays.asList("png", "jpg", "jpeg"), 1000000, avatarToSave.getStorageKey());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      throw new BusinessLogicException("You can't change OAuth2 provider's avatar.");
    }
  }
}
