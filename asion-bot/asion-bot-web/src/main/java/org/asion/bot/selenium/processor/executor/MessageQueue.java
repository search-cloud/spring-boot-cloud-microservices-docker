package org.asion.bot.selenium.processor.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue<T extends Serializable> {

    private static final Logger logger = LoggerFactory.getLogger(MessageQueue.class);

    private final int MAX_SIZE = 1000;
    private BlockingQueue<T> messageQueue = new LinkedBlockingQueue<>(MAX_SIZE);
    private static MessageQueue instance = new MessageQueue();

    private MessageQueue() {}

    static <E extends Serializable>  MessageQueue<E> getInstance() {
        return instance;
    }

    void produce(T message) {
        if (messageQueue.size() == MAX_SIZE) {
            logger.debug("messageQueue is full: {}, waiting for consume!", MAX_SIZE);
        }
        try {
            messageQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    T consume() {
        T message = null;
        if (messageQueue.isEmpty()) {
            logger.debug("messageQueue is empty, waiting for produce!");
            // sleep 3 seconds.
//            Companion.sleep(3);
        }
        try {
            message = messageQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return message;
    }
}