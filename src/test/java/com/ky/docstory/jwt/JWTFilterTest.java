package com.ky.docstory.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

public class JWTFilterTest {

    @Mock
    private JWTUtil jwtUtil;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    private JWTFilter jwtFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtFilter = new JWTFilter(jwtUtil);
    }

    @Test
    public void testNoToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/test");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            jwtFilter.doFilterInternal(request, response, filterChain);
        });
        assertEquals(DocStoryResponseCode.JWT_ILLEGAL.getMessage(), exception.getMessage());
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    public void testInvalidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/test");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer invalidtoken");
        when(jwtUtil.validateToken("invalidtoken")).thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            jwtFilter.doFilterInternal(request, response, filterChain);
        });
        assertEquals(DocStoryResponseCode.JWT_UNAUTHORIZED.getMessage(), exception.getMessage());
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    public void testValidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/api/test");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validtoken");
        when(jwtUtil.validateToken("validtoken")).thenReturn(true);
        when(jwtUtil.getProviderIdFromToken("validtoken")).thenReturn("provider123");

        jwtFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }
}
