package com.awesome.testing.controller;

import com.awesome.testing.dto.CustomMessage;
import com.awesome.testing.service.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class QueueController {

    private final MessageSender messageSender;

    @Autowired
    public QueueController(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @PostMapping(value = "/queue", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendToQueue(@RequestBody CustomMessage customMessage) {
        messageSender.sendMessage(customMessage);
        return ResponseEntity.status(HttpStatus.OK).body("Message sent successfully");
    }

}
