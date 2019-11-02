package com.awesome.testing.service;

import com.awesome.testing.dto.Information;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProviderService {

    private final RestTemplate restTemplate;

    @Value("${backend.url}")
    private String backendUrl;

    public ProviderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void overrideBackendUrl(String newUrl) {
        backendUrl = newUrl;
    }

    public ResponseEntity<Information> getResponseForName(String name) {
        String url = String.format("%s/information?name=%s", backendUrl, name);
        return restTemplate.getForEntity(url, Information.class);
    }

    public ResponseEntity<Information> add(Information information) {
        String url = String.format("%s/information", backendUrl);
        return restTemplate.postForEntity(url, information, Information.class);
    }
}
