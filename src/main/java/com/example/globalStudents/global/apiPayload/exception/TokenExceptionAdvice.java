package com.example.globalStudents.global.apiPayload.exception;

import com.example.globalStudents.global.apiPayload.ApiResponse;
import com.example.globalStudents.global.apiPayload.code.ErrorReasonDTO;
import com.example.globalStudents.global.apiPayload.code.status.ErrorStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class TokenExceptionAdvice extends ResponseEntityExceptionHandler{

    @org.springframework.web.bind.annotation.ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> handleInsufficientAuthenticationException(Exception e, WebRequest request) {

        return handleExceptionInternalFalse(e, ErrorStatus.TOKEN_ERROR, HttpHeaders.EMPTY, ErrorStatus.TOKEN_ERROR.getHttpStatus(),request, ErrorStatus.TOKEN_ERROR.getMessage());
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException(Exception e, WebRequest request) {


        return handleExceptionInternalFalse(e, ErrorStatus.TOKEN_MALFUNCTION, HttpHeaders.EMPTY, ErrorStatus.TOKEN_MALFUNCTION.getHttpStatus(),request, ErrorStatus.TOKEN_MALFUNCTION.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(Exception e, WebRequest request) {

        return handleExceptionInternalFalse(e, ErrorStatus.TOKEN_EXPIRED, HttpHeaders.EMPTY, ErrorStatus.TOKEN_EXPIRED.getHttpStatus(),request, ErrorStatus.TOKEN_EXPIRED.getMessage());

    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
                                                           HttpHeaders headers, HttpServletRequest request) {

        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(),reason.getMessage(),null);


        WebRequest webRequest = new ServletWebRequest(request);

        return handleExceptionInternal(
                e,
                body,
                headers,
                reason.getHttpStatus(),
                webRequest
        );
    }

    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorStatus errorCommonStatus,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorPoint);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                status,
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, ErrorStatus errorCommonStatus,
                                                               WebRequest request, Map<String, String> errorArgs) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorArgs);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                errorCommonStatus.getHttpStatus(),
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorCommonStatus,
                                                                     HttpHeaders headers, WebRequest request) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                errorCommonStatus.getHttpStatus(),
                request
        );
    }

}
