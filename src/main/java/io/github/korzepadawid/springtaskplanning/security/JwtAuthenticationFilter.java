package io.github.korzepadawid.springtaskplanning.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private final JwtProvider jwtProvider;
  private final UserDetailsService userDetailsService;

  private static final String HTTP_AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_TOKEN_PREFIX = "Bearer ";
  private static final int BEARER_TOKEN_PREFIX_LENGTH = 7;

  public JwtAuthenticationFilter(JwtProvider jwtProvider, UserDetailsService userDetailsService) {
    this.jwtProvider = jwtProvider;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = getJwtFromHeader(request);

      if (StringUtils.hasText(jwt) && jwtProvider.verify(jwt)) {
        String userEmail = jwtProvider.extractEmailFromJwt(jwt);
        UserDetails principal = userDetailsService.loadUserByUsername(userEmail);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      log.error("Can't set authenticated user. " + e.getMessage());
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtFromHeader(HttpServletRequest httpServletRequest) {
    String authorizationHeader = httpServletRequest.getHeader(HTTP_AUTHORIZATION_HEADER);

    if (StringUtils.hasText(authorizationHeader)
        && authorizationHeader.startsWith(BEARER_TOKEN_PREFIX)) {
      return authorizationHeader.substring(BEARER_TOKEN_PREFIX_LENGTH);
    }

    log.error("Can't extract jwt from request.");
    return null;
  }
}
