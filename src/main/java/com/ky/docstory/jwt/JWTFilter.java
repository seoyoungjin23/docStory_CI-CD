package com.ky.docstory.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTFilter(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.equals("/login") || requestURI.equals("/default-ui.css") || requestURI.equals("/favicon.ico") || requestURI.equals("/swagger") || requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);

        try {
            if (token == null) {
                throw new BusinessException(DocStoryResponseCode.JWT_ILLEGAL);
            }

            if (jwtUtil.validateToken(token)) {
                String providerId = jwtUtil.getProviderIdFromToken(token);
                User user = userRepository.findByProviderId(providerId)
                        .orElseThrow(() -> new BusinessException(DocStoryResponseCode.USER_NOT_FOUND));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new BusinessException(DocStoryResponseCode.JWT_UNAUTHORIZED);
            }
        } catch (BusinessException be) {
            jsonErrorResponse(response, be.getErrorCode());
            return;
        } catch (Exception e) {
            jsonErrorResponse(response, DocStoryResponseCode.INTERNAL_SERVER_ERROR);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }


    private void jsonErrorResponse(HttpServletResponse response, DocStoryResponseCode errorCode)
            throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", errorCode.getCode());
        responseMap.put("message", errorCode.getMessage());
        responseMap.put("data", null);

        String jsonResponse = objectMapper.writeValueAsString(responseMap);
        response.getWriter().write(jsonResponse);
    }
}
