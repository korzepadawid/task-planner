package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.model.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  User getUserById(Long id);

  UserResponse findUserById(Long id);

  byte[] findAvatarByUserId(Long id);

  void setAvatar(Long id, MultipartFile file) throws IOException;

}
