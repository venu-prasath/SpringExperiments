package com.venuprasath.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    Logger logger = LoggerFactory.getLogger(Receiver.class);
    CountDownLatch latch = new CountDownLatch(1);

    public void receive(String message) {
        logger.info("Received: <"+message+">");
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
