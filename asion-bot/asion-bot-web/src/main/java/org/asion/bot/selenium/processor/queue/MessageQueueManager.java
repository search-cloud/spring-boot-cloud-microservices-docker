package org.asion.bot.selenium.processor.queue;

import org.asion.bot.selenium.processor.extractor.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * 总消息队列管理
 *
 * @author Asion
 */
public class MessageQueueManager implements IMessageQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageQueueManager.class);

    /**
     * 消息总队列
     */
    final BlockingQueue<Message> messageQueue;

    private final List<BlockingQueue<Message>> subMessageQueues;

    private MessageQueueManager() {
        messageQueue = new LinkedTransferQueue<>();
        subMessageQueues = new ArrayList<>();
    }

    public void put(Message msg) {
        try {
            messageQueue.put(msg);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Message take() {
        try {
            return messageQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    /**
     * 均衡获取一个子队列。
     *
     * @return 获取一个子队列
     */
    public BlockingQueue<Message> getSubQueue() {
        int errorCount = 0;
        for (;;) {
            if (subMessageQueues.isEmpty()) {
                return null;
            }
            int index = (int) (System.nanoTime() % subMessageQueues.size());
            try {
                return subMessageQueues.get(index);
            } catch (Exception e) {
                //出现错误表示，在获取队列大小之后，队列进行了一次删除操作
                LOGGER.error("获取子队列出现错误", e);
                if ((++errorCount) < 3) {
                    continue;
                }
            }
        }
    }

    /**
     * 分发消息，负责把消息从大队列塞到小队列里
     *
     * @author Asion
     */
    static class DispatchMessageTask implements Runnable {
        @Override
        public void run() {
            BlockingQueue<Message> subQueue;
            for (; ; ) {
                // 如果没有数据，则阻塞在这里
                Message msg = MessageQueueFactory.getMessageQueue().take();

                // 如果为空，则表示没有Session机器连接上来，需要等待，直到有Session机器连接上来
                while ((subQueue = getInstance().getSubQueue()) == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                // 把消息放到小队列里
                try {
                    subQueue.put(msg);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

    static MessageQueueManager getInstance() {
        return new MessageQueueManager();
    }

}