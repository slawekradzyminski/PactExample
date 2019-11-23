package com.awesome.testing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProviderService {

    @Value("${backend.url}")
    private String backendUrl;

    public void overrideBackendUrl(String newUrl) {
        backendUrl = newUrl;
    }

    public ResponseEntity<Information> getResponseForName(String name) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/information?name=%s", backendUrl, name);
        return restTemplate.getForEntity(url, Information.class);
    }
}
