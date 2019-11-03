package com.awesome.testing.error;

import org.springframework.http.ResponseEntity;

public class ErrorProxy {

    public boolean isProxied(ResponseEntity<?> response) {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

}
