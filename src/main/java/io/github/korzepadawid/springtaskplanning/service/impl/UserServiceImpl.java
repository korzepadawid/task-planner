package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.Avatar;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.AvatarRepository;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import io.github.korzepadawid.springtaskplanning.service.StorageService;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import java.io.IOException;
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
  public UserResponse findUserByEmail(String email) {
    return new UserResponse(findUser(email));
  }

  /**
   * Only users with local-based authentication can update their avatars. If the avatar's storage
   * key is empty, it will upload a new photo. Otherwise, it will replace an existing object.
   */
  @Override
  @Transactional
  public void setAvatar(String userEmail, MultipartFile file) throws IOException {
    User user = findUser(userEmail);
    if (user.getAuthProvider().equals(AuthProvider.LOCAL)) {
      Avatar existingAvatar = user.getAvatar();
      if (existingAvatar == null) {
        String storageKey = storageService.uploadPhoto(file);
        Avatar avatar = new Avatar();
        avatar.setStorageKey(storageKey);
        avatar.setUser(user);
        avatarRepository.save(avatar);
      } else {
        storageService.replacePhoto(existingAvatar.getStorageKey(), file);
      }
    }
  }

  private User findUser(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
  }
}
