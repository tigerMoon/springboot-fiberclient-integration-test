package com.social.credits.web.client;

import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStreamReader;

public class ScClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScClientHttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        LOGGER.info("Request {} {}", request.getMethod(), request.getURI());
        LOGGER.info("Request headers {}", request.getHeaders().toSingleValueMap());

        ClientHttpResponse response = execution.execute(request, body);

        LOGGER.info("Response status: {}", response.getStatusCode());
        LOGGER.info("Response content type: {}", response.getHeaders().getContentType());

        if (response.getHeaders().getContentType() != null) {
            if ((response.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON) ||
                    response.getHeaders().getContentType().includes(MediaType.TEXT_PLAIN) ||
                    response.getHeaders().getContentType().includes(MediaType.APPLICATION_XML) ||
                    response.getHeaders().getContentType().includes(MediaType.APPLICATION_OCTET_STREAM) ||
                    response.getHeaders().getContentType().includes(MediaType.TEXT_HTML))) {
                String str = CharStreams.toString(new InputStreamReader(response.getBody(), "UTF-8"));
                if (str.length() > 200) {
                    str = str.substring(0, 200);
                }
                LOGGER.info("Response body: {}", str);
            }
        }
        return response;
    }
}
