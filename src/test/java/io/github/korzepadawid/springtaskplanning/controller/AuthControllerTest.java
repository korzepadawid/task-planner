package io.github.korzepadawid.springtaskplanning.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import io.github.korzepadawid.springtaskplanning.dto.AuthLoginRequest;
import io.github.korzepadawid.springtaskplanning.dto.AuthLoginResponse;
import io.github.korzepadawid.springtaskplanning.dto.AuthRegisterRequest;
import io.github.korzepadawid.springtaskplanning.dto.UserResponse;
import io.github.korzepadawid.springtaskplanning.exception.UserAlreadyExistsException;
import io.github.korzepadawid.springtaskplanning.model.AuthProvider;
import io.github.korzepadawid.springtaskplanning.model.User;
import io.github.korzepadawid.springtaskplanning.service.AuthService;
import io.github.korzepadawid.springtaskplanning.util.JsonSerializer;
import io.github.korzepadawid.springtaskplanning.util.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  @Mock private AuthService authService;

  @InjectMocks private AuthController authController;

  private MockMvc mockMvc;

  public static final String REGISTER_URL = "/api/v1/auth/register";
  public static final String LOGIN_URL = "/api/v1/auth/login";

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
  }

  @Test
  void shouldReturn400WhenUserAlreadyExists() throws Exception {
    AuthRegisterRequest userRegisterRequest = UserFactory.getUserRegisterRequest();
    when(authService.register(any())).thenThrow(UserAlreadyExistsException.class);

    mockMvc
        .perform(
            post(REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonSerializer.serialize(userRegisterRequest)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturn400WhenValidationErrors() throws Exception {
    AuthRegisterRequest userRegisterRequest = UserFactory.getUserRegisterRequest();
    userRegisterRequest.setEmail("not an email");

    mockMvc
        .perform(
            post(REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonSerializer.serialize(userRegisterRequest)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturn201WhenNewUserHasBeenCreated() throws Exception {
    User user = UserFactory.getUser(AuthProvider.LOCAL);
    UserResponse userResponse = new UserResponse(user);
    when(authService.register(any())).thenReturn(userResponse);

    mockMvc
        .perform(
            post(REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonSerializer.serialize(UserFactory.getUserRegisterRequest())))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(jsonPath("$.email", is(user.getEmail())));
  }

  @Test
  void shouldReturn200AndAccessTokenWhenValidCredentials() throws Exception {
    AuthLoginRequest authLoginRequest = new AuthLoginRequest();
    authLoginRequest.setEmail("test@email.com");
    authLoginRequest.setPassword("cool_password");
    String jwtToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9"
            + ".AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
    when(authService.login(any())).thenReturn(new AuthLoginResponse(jwtToken));

    mockMvc
        .perform(
            post(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonSerializer.serialize(authLoginRequest)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.accessToken", is(jwtToken)));
  }
}
