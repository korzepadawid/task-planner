package io.github.korzepadawid.springtaskplanning.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.korzepadawid.springtaskplanning.dto.ErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger log = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String responseJson =
          objectMapper.writeValueAsString(
              new ErrorResponse("Access denied.", HttpStatus.FORBIDDEN.value()));
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(responseJson);
    } catch (JsonProcessingException exception) {
      log.error("Can't process JSON");
    }
  }
}
