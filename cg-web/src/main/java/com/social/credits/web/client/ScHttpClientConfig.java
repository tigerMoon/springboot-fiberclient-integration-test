package com.social.credits.web.client;

import co.paralleluniverse.fibers.httpclient.FiberHttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.social.credits")
public class ScHttpClientConfig {

    @Value("${fiber.http.client.io.thread:10}")
    private int httpIoThread;

    @Value("${fiber.http.client.conn.route:1000}")
    private int httpConnPerRoute;

    @Value("${fiber.http.client.conn.max:10000}")
    private int httpConnMax;

    @Value("${restTemplate.connection.timeout:50000}") //50 seconds
    private int connectionRequestTimeout;

    @Value("${restTemplate.read.timeout:50000}") //50 seconds
    private int readTimeout;

    /**
     * set restTemplate http client
     * construct use Apache httpclient
     * if use restTemplate.getInterceptors().add(new ScClientHttpRequestInterceptor()); may get null from result
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        HttpClient httpClient = HttpClientBuilder.create().build();  // if use this ,it works fine.
        HttpClient httpClient =FiberHttpClientBuilder.
                create(httpIoThread).
                setMaxConnPerRoute(httpConnPerRoute).
                setMaxConnTotal(httpConnMax).build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectionRequestTimeout(connectionRequestTimeout);
        factory.setReadTimeout(readTimeout);
        // construct interceptor
        List<ClientHttpRequestInterceptor> interceptorList = new ArrayList<>();
        interceptorList.add(new ScClientHttpRequestInterceptor());
        InterceptingClientHttpRequestFactory interceptorFactory = new InterceptingClientHttpRequestFactory(new BufferingClientHttpRequestFactory(factory), interceptorList);
        restTemplate.setRequestFactory(interceptorFactory);
        return restTemplate;
    }
}
