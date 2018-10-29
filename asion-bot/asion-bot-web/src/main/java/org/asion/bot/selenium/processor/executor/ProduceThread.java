package org.asion.bot.selenium.processor.executor;

import org.asion.bot.helper.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ProduceThread<T extends Serializable> implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ProduceThread.class);

    protected Object message;

    public ProduceThread(Object message) {
        this.message = message;
    }

    @Override
    public void run() {
        List<T> messages = produce();
        messages.forEach(msg -> MessageQueue.<T>getInstance().produce(msg));
        logger.debug("produce count: {}, {}", Counter.INSTANCE.safeAdd().get(), message);
    }


    protected List<T> produce() {
        // TODO do something here
        // sleep 3 seconds to simulate produce business.
//        ThreadPoolManager.Companion.sleep(3);
        return Collections.<T>singletonList((T) message);
    }

}