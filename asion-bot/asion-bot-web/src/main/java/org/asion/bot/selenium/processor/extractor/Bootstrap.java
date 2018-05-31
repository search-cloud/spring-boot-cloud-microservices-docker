package org.asion.bot.selenium.processor.extractor;

/**
 * @author Asion.
 * @since 2018/5/16.
 */
public class Bootstrap {

    public static void main(String[] args) {
        // 往消息队列里添加一条消息
//        IMessageQueue messageQueue = MessageQueueFactory.getMessageQueue();
//        Message msg = new ItemMessage();
//        messageQueue.put(msg);

        new QuickItemExtractor(new ExtractServiceImpl()).extract();
    }
}
