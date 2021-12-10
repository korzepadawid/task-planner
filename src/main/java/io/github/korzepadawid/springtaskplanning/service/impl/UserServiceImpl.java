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
        .map(
            user -> {
              Avatar userAvatar = user.getAvatar();
              if (userAvatar != null) {
                try {
                  return storageService.downloadFile(userAvatar.getStorageKey());
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
              throw new ResourceNotFoundException("Avatar not found");
            })
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  @Transactional
  public void setAvatar(String email, MultipartFile file) {
    userRepository
        .findByEmail(email)
        .ifPresentOrElse(
            user -> {
              Avatar userAvatar = user.getAvatar();
              String storageKey = "";
              if (userAvatar == null) {
                storageKey = UUID.randomUUID().toString();
                Avatar avatar = new Avatar();
                avatar.setUser(user);
                avatar.setStorageKey(storageKey);
                avatarRepository.save(avatar);
              } else {
                storageKey = userAvatar.getStorageKey();
              }
              try {
                storageService.putFile(
                    file, Arrays.asList("png", "jpeg", "jpg"), 1000000, storageKey);
              } catch (IOException e) {
                e.printStackTrace();
              }
            },
            () -> {
              throw new ResourceNotFoundException("User doesn't exist");
            });
  }

  private User findUserWithEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
  }
}
