package com.social.credits.web.service;

import org.springframework.stereotype.Service;

/**
 * Created by tiger on 16-10-13.
 */
@Service
public class SimpleServiceImpl implements SimpleService {

    @Override
    public String getSomeData() {
        return "ok";
    }
}
