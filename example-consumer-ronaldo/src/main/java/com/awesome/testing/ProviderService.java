package com.awesome.testing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProviderService {

    private static final String RONALDO = "/information?name=Ronaldo";

    @Value("${backend.url}")
    private String backendUrl;

    public void overrideBackendUrl(String newUrl) {
        backendUrl = newUrl;
    }

    public Information getInformation() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendUrl + RONALDO, Information.class);
    }
}
