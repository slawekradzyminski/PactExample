package com.awesome.testing.service;

import com.awesome.testing.dto.information.IdNotAwareInformation;
import com.awesome.testing.dto.information.Information;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InformationClient {

    private final RestTemplate restTemplate;

    @Value("${backend.url}")
    private String backendUrl;

    public InformationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void overrideBackendUrl(String newUrl) {
        backendUrl = newUrl;
    }

    public ResponseEntity<Information> getResponseForName(String name) {
        String url = String.format("%s/information?name=%s", backendUrl, name);
        return restTemplate.getForEntity(url, Information.class);
    }

    public ResponseEntity<Information> add(IdNotAwareInformation information) {
        String url = String.format("%s/information", backendUrl);
        return restTemplate.postForEntity(url, information, Information.class);
    }

    public ResponseEntity<Information> updateViaPut(IdNotAwareInformation information, long id) {
        String url = String.format("%s/information/%s", backendUrl, id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<IdNotAwareInformation> httpEntity = new HttpEntity<>(information, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Information.class);
    }
}
