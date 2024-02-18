package com.nabilaitnacer.portalusermanager.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilaitnacer.portalusermanager.domain.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.nabilaitnacer.portalusermanager.constant.SecurityConstant.ACCESS_DENIED_MESSAGE;

public class JwtAccessDenieHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HttpResponse httpResponse = HttpResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED.toString())
                .httpStatusCode(HttpStatus.UNAUTHORIZED.value())
                .reason(HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase())
                .message(ACCESS_DENIED_MESSAGE)
                .build();
        response.setContentType("application/json");
        response.setStatus(httpResponse.getHttpStatusCode());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}
