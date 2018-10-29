package org.asion.bot.selenium.processor.executor;

import org.asion.bot.helper.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class ConsumeThread<T extends Serializable> implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeThread.class);

    @Override
    public void run() {
        for (; ; ) {
            T message = MessageQueue.<T>getInstance().consume();
            consume(message);
            logger.debug("consume count: {}, {}", Counter.INSTANCE.safeAdd().get(), message);
        }
    }

    protected void consume(T message) {
        //TODO do something here
        // sleep 3 seconds to simulate consume business.
//        ThreadPoolManager.Companion.sleep(3);
        logger.debug("consume: " + message);
    }

}