package com.nabilaitnacer.portalusermanager.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilaitnacer.portalusermanager.domain.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.nabilaitnacer.portalusermanager.constant.SecurityConstant.FORBIDDEN_MESSAGE;

public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        HttpResponse httpResponse = HttpResponse.builder()
                        .httpStatus(HttpStatus.FORBIDDEN.toString())
                        .httpStatusCode(HttpStatus.FORBIDDEN.value())
                        .reason(HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase())
                        .message(FORBIDDEN_MESSAGE)
                        .build();
        response.setContentType("application/json");
        response.setStatus(httpResponse.getHttpStatusCode());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}
