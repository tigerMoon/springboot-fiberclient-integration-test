package com.social.credits.web;

import com.social.credits.web.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor())
                .excludePathPatterns("/api/client/report/log");

    }


    @Bean
    protected LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }


}