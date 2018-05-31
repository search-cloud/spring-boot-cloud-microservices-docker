package org.asion.bot.selenium.processor.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 队列消费任务
 *
 * @author Asion.
 * @since 2018/5/16.
 */
public class MessageConsumerTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(MessageConsumerTask.class);

    private Message message;

    public MessageConsumerTask(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        // TODO implement
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": " + message);
//        logger.warn(message.toString());
    }
}
