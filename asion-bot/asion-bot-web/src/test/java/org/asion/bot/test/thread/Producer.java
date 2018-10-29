package org.asion.bot.test.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

public class Producer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private final Queue<Integer> sharedQ;

    public Producer(Queue<Integer> sharedQ) {
        super("Producer");
        this.sharedQ = sharedQ;
    }

    @Override
    public void run() {

        for (int i = 0; i < 4; i++) {

            synchronized (sharedQ) {
                //waiting condition - wait until Queue is not empty
                while (sharedQ.size() >= 1) {
                    try {
                        logger.debug("Queue is full, waiting");
                        sharedQ.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                logger.debug("producing : " + i);
                sharedQ.add(i);
                sharedQ.notifyAll();
            }
        }
    }
}