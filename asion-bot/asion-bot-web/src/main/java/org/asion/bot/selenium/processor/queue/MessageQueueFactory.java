package org.asion.bot.selenium.processor.queue;

/**
 * @author Asion.
 * @since 2018/5/16.
 */
class MessageQueueFactory {

    private static MessageQueueManager messageQueueManager = MessageQueueManager.getInstance();

    static MessageQueueManager getMessageQueue() {
        return messageQueueManager;
    }
}
