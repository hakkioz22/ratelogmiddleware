package com.project.loggingmiddleware.interceptor;

import com.project.loggingmiddleware.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Autowired
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ipAddress = request.getRemoteAddr();
        String timestamp = LocalDateTime.now().toString();
        String message = "Timestamp: " + timestamp + ", IP: " + ipAddress + ", Request: " + request.getMethod() + " " + request.getRequestURI();
        logService.log(message, "console");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String timestamp = LocalDateTime.now().toString();
        String message = "Timestamp: " + timestamp + ", Response Status: " + response.getStatus();
        if (ex != null) {
            message += ", Error: " + ex.getMessage();
        }
        logService.log(message, "file");
    }
}
