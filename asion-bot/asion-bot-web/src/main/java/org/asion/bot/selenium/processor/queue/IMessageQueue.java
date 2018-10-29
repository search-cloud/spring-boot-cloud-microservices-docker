package org.asion.bot.selenium.processor.queue;


import org.asion.bot.selenium.processor.extractor.Message;

import java.util.concurrent.BlockingQueue;

/**
 * @author Asion.
 * @since 2018/5/16.
 */
public interface IMessageQueue {
    void put(Message msg);
    Message take();
    BlockingQueue<Message> getSubQueue();
}
