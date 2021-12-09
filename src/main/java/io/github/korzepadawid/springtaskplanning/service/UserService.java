package io.github.korzepadawid.springtaskplanning.service;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  UserResponse findUserByEmail(String email);

  byte[] findAvatarByUserId(Long id);

  void setAvatar(String userEmail, MultipartFile file) throws IOException;
}
