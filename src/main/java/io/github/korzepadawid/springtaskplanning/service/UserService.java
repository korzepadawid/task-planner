package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.model.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  User getUserById(Long userId);

  UserResponse findUserById(Long userId);

  byte[] findAvatarByUserId(Long userId);

  void saveOrUpdateAvatarByUserId(Long userId, MultipartFile multipartFile) throws IOException;
}
