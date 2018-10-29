package org.asion.bot.test.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

public class Consumer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final Queue<Integer> sharedQ;

    public Consumer(Queue<Integer> sharedQ) {
        super("Consumer");
        this.sharedQ = sharedQ;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (sharedQ) {
                // waiting condition - wait until Queue is not empty
                while (sharedQ.size() == 0) {
                    try {
                        logger.debug("Queue is empty, waiting");
                        sharedQ.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                if (sharedQ.size() != 0) {
                    Integer number = sharedQ.poll();
                    logger.debug("consuming : " + number);
                    sharedQ.notifyAll();

                    // termination condition
                    if (number == 3) {
                        break;
                    }
                }
            }
        }
    }
}