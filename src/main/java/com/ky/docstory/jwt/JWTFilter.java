package com.ky.docstory.jwt;

import com.ky.docstory.auth.CustomOAuth2User;
import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.equals("/login") || requestURI.equals("/default-ui.css") || requestURI.equals("/favicon.ico") || requestURI.equals("/swagger")) {
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
                String nickname = jwtUtil.getNicknameFromToken(token);
                String profileImage = jwtUtil.getProfileImageFromToken(token);

                CustomOAuth2User customUser = new CustomOAuth2User(null, providerId, nickname, profileImage);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUser, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new BusinessException(DocStoryResponseCode.JWT_UNAUTHORIZED);
            }

        } catch (BusinessException businessException) {
            throw businessException;
        } catch (Exception e) {
            throw new BusinessException(DocStoryResponseCode.INTERNAL_SERVER_ERROR);
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
}
