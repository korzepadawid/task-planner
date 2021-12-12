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
  @Transactional(readOnly = true)
  public UserResponse findUserByEmail(String email) {
    return new UserResponse(findUserWithEmail(email));
  }

  @Override
  @Transactional(readOnly = true)
  public byte[] findAvatarByUserId(Long id) {
    return userRepository
        .findById(id)
        .filter(user -> user.getAvatar() != null)
        .map(
            user -> {
              try {
                return storageService.downloadFile(user.getAvatar().getStorageKey());
              } catch (IOException e) {
                e.printStackTrace();
              }
              return new byte[0];
            })
        .orElseThrow(() -> new ResourceNotFoundException("Not found"));
  }

  /**
   * Changing the avatar feature is impossible for OAuth2 users. It checks if the avatar already
   * exists, otherwise, it creates a new one.
   */
  @Override
  @Transactional
  public void setAvatar(String email, MultipartFile file) {
    userRepository
        .findByEmail(email)
        .ifPresentOrElse(
            user -> {
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
                      file,
                      Arrays.asList("png", "jpg", "jpeg"),
                      1000000,
                      avatarToSave.getStorageKey());
                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else {
                throw new BusinessLogicException("You can't change OAuth2 provider's avatar.");
              }
            },
            () -> {
              throw new ResourceNotFoundException("User doesn't exist.");
            });
  }

  private User findUserWithEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
  }
}
