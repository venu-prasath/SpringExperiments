package com.venuprasath.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class Receiver {

    Logger logger = LoggerFactory.getLogger(Receiver.class);

    AtomicLong counter = new AtomicLong();

    public void receiveMessage(String message) {
        logger.info("Received message: <"+message+">");
        counter.incrementAndGet();
    }

    public long getCounter() {
        return counter.get();
    }
}
