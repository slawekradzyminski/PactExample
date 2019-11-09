package com.awesome.testing.service;

import com.awesome.testing.dto.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageListener {

    @Value("${queue.name}")
    private String queueName;

    @RabbitListener(queues = "rabbit")
    public void receiveMessage(final CustomMessage customMessage) {
        log.info("Received message as specific class: {}", customMessage.toString());
    }

}
