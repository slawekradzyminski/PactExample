package com.awesome.testing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    @Value("${backend.url}")
    private String backendUrl;

    public void overrideBackendUrl(String newUrl) {
        backendUrl = newUrl;
    }

}
