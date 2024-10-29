package com.project.loggingmiddleware.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final Map<String, RequestInfo> requestMap = new ConcurrentHashMap<>();
    private static final int MAX_REQUEST = 3;
    private static final int TIME_WINDOW_SECONDS = 10;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ipAddress = request.getRemoteAddr();
        LocalDateTime now = LocalDateTime.now();

        // Get or initialize RequestInfo for the IP
        requestMap.putIfAbsent(ipAddress, new RequestInfo());
        RequestInfo requestInfo = requestMap.get(ipAddress);

        synchronized (requestInfo) {
            // Remove old requests beyond the time window
            requestInfo.removeOldRequests(now);

            // Check request count within the time window
            if (requestInfo.getRequestCount() >= MAX_REQUEST) {
                response.setStatus(429);
                try {
                    response.getWriter().write("Rate limit exceeded. Try again later.");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }

            requestInfo.addRequest(now);
        }


        return true;
    }

    private static class RequestInfo {
        private final LinkedList<LocalDateTime> requestTimestamps = new LinkedList<>();

        public void addRequest(LocalDateTime timestamp) {
            requestTimestamps.addLast(timestamp);
        }

        public void removeOldRequests(LocalDateTime currentTime) {
            while (!requestTimestamps.isEmpty() && requestTimestamps.getFirst().isBefore(currentTime.minusSeconds(TIME_WINDOW_SECONDS))) {
                requestTimestamps.removeFirst();
            }
        }

        public int getRequestCount() {
            return requestTimestamps.size();
        }
    }
}
