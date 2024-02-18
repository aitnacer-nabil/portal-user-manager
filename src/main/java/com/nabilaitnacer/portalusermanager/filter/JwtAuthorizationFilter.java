package com.nabilaitnacer.portalusermanager.filter;

import com.nabilaitnacer.portalusermanager.constant.SecurityConstant;
import com.nabilaitnacer.portalusermanager.utility.JwtTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.nabilaitnacer.portalusermanager.constant.SecurityConstant.TOKEN_PREFIX;
import static org.springframework.http.HttpStatus.OK;

/**
 * Filter for JWT authorization.
 * It extends OncePerRequestFilter to ensure a single execution per request dispatch.
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isOptionsHttpRequest(request)) {
            response.setStatus(OK.value());
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (isNotValidHeader(authorizationHeader)) {
                filterChain.doFilter(request, response);
                return; // return to the client

            }
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            processJwtToken(token, request);
        }
    }
    private boolean isOptionsHttpRequest(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD);
    }

    private boolean isNotValidHeader(String authorizationHeader) {
        return StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith(TOKEN_PREFIX);
    }

    private void processJwtToken(String token, HttpServletRequest request) {
        String username = getUsernameFromToken(token);
        //check if the token is valid
        // user don't have an authentication context already set up by the application
        // (i.e. the user is not already authenticated)
        if (jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
            Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
        }
    }
    private String getUsernameFromToken(String token) {
        return jwtTokenProvider.getSubject(token);
    }
}
