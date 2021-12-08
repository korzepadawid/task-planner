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

  public static final String GET_USERS_ME_URL = "/api/v1/users/me";
  public static final String JWT_TOKEN =
      "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZXN0QGd"
          + "tYWlsLmNvbSIsImV4cCI6MTYzODk4MzUzN30.BwaQ4k1EhAT7CW6XpKMJ9xry8Ma220iMF0Rc0BqVNOA";

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  void shouldReturn404WhenUserDoesNotExist() throws Exception {
    when(userService.findUserByEmail(any())).thenThrow(ResourceNotFoundException.class);

    mockMvc
        .perform(get(GET_USERS_ME_URL).header("Authorization", "Bearer " + JWT_TOKEN))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturn200WhenUserExists() throws Exception {
    User user = UserFactory.getUser(AuthProvider.GOOGLE);
    when(userService.findUserByEmail(any())).thenReturn(new UserResponse(user));

    mockMvc
        .perform(get(GET_USERS_ME_URL).header("Authorization", "Bearer " + JWT_TOKEN))
        .andExpect(status().isOk());
  }
}
