package io.github.korzepadawid.springtaskplanning.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.service.UserService;
import io.github.korzepadawid.springtaskplanning.util.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  private MockMvc mockMvc;

  public static final String API_V_1_USERS_ME = "/api/v1/users/me";
  public static final String API_V_1_USERS_ME_AVATAR = "/api/v1/users/1/avatar";

  @BeforeEach
  void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(GlobalControllerExceptionHandler.class)
            .build();
  }

  @Test
  void shouldReturn404WhenUserDoesNotExist() throws Exception {
    when(userService.findUserByEmail(any())).thenThrow(ResourceNotFoundException.class);

    mockMvc.perform(get(API_V_1_USERS_ME)).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturn200WhenUserExists() throws Exception {
    User user = UserFactory.getUser(AuthProvider.GOOGLE);
    when(userService.findUserByEmail(any())).thenReturn(new UserResponse(user));

    mockMvc.perform(get(API_V_1_USERS_ME)).andExpect(status().isOk());
  }

  @Test
  void shouldReturn404WhenGetAvatarAndUserDoesNotExist() throws Exception {
    when(userService.findAvatarByUserId(any())).thenThrow(ResourceNotFoundException.class);

    mockMvc.perform(get(API_V_1_USERS_ME_AVATAR)).andExpect(status().isNotFound());
  }

  @Test
  void shouldReturn200WhenGetAvatarUserExists() throws Exception {
    when(userService.findAvatarByUserId(any())).thenReturn(new byte[0]);

    mockMvc.perform(get(API_V_1_USERS_ME_AVATAR)).andExpect(status().isOk());
  }
}
