package com.example.globalStudents.global.auth.filter;

import com.example.globalStudents.global.apiPayload.ApiResponse;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setUnsuccessfulResponse(response);
    }

    private void setUnsuccessfulResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        String text = mapper.writeValueAsString(ApiResponse.onFailure(ErrorStatus._FORBIDDEN.getCode().toString(),ErrorStatus._FORBIDDEN.getMessage(),""));

        response.getWriter().print(text);
    }
}
