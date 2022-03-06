package io.github.korzepadawid.springtaskplanning.service.impl;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.Avatar;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.repository.AvatarRepository;
import io.github.korzepadawid.springtaskplanning.repository.UserRepository;
import io.github.korzepadawid.springtaskplanning.service.StorageService;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AvatarRepository avatarRepository;
  private final StorageService storageService;

  private static final int MAX_FILE_SIZE_IN_BYTES = 1000000;
  private static final List<String> POSSIBLE_IMAGE_FILE_EXTENSIONS =
      new ArrayList<>(Arrays.asList("png", "jpg", "jpeg"));

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
        return storageService.downloadFileByStorageKey(user.getAvatar().getStorageKey());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    throw new ResourceNotFoundException("Avatar not found");
  }

  @Override
  @Transactional
  public void saveOrUpdateAvatarByUserId(Long userId, MultipartFile multipartFile) {
    User user = getUserById(userId);
    Avatar avatar;

    if (user.getAvatar() != null) {
      avatar = user.getAvatar();
    } else {
      Avatar newAvatar = createAvatarWithUser(user);
      avatar = avatarRepository.save(newAvatar);
    }
    try {
      storageService.putFile(
          multipartFile,
          POSSIBLE_IMAGE_FILE_EXTENSIONS,
          MAX_FILE_SIZE_IN_BYTES,
          avatar.getStorageKey());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Avatar createAvatarWithUser(User user) {
    Avatar avatar = new Avatar();
    avatar.setStorageKey(UUID.randomUUID().toString());
    avatar.setUser(user);
    return avatar;
  }
}
