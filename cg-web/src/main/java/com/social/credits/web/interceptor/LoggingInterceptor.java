package com.social.credits.web.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private static final String SC_ID_HEADER = "sc-id";
    private static final String SC_ID_KEY = "scId";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);
    private AtomicLong counter = new AtomicLong();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("sc_id", counter.incrementAndGet());
        String scId = request.getHeader(SC_ID_HEADER);
        if (StringUtils.isBlank(scId)) {
            scId = UUID.randomUUID().toString();
        }
        MDC.put(SC_ID_KEY, scId);
        logRequest(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    private void logRequest(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if ("GET".equalsIgnoreCase(request.getMethod()) && !StringUtils.isBlank(queryString)) {
            try {
                queryString = URLDecoder.decode(queryString, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("convert queryString error ", e);
            }
        }
        LOGGER.info("Request: [{}] [{}] [{}] [{}]", request.getMethod(), request.getRequestURL(), queryString, request.getHeader(SC_ID_HEADER));
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(":").append(request.getHeader(headerName)).append("\n");
        }
        LOGGER.info("Request header: {}" + headers.toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.info("Response: [{}]", response.getStatus());
        MDC.remove(SC_ID_KEY);
    }
}
