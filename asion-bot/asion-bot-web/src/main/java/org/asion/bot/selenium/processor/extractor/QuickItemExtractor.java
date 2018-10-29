package org.asion.bot.selenium.processor.extractor;

import org.asion.bot.selenium.processor.queue.MessageBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class QuickItemExtractor extends AbstractExtractor {

    /**
     * 任务线程池
     */
    private ThreadPoolExecutor threadPool;
    private MessageBlockingQueue<Message> producerQueue;
    private static final Logger logger = LoggerFactory.getLogger(QuickItemExtractor.class);
    /**
     * 抽取业务服务
     */
    private ExtractService extractService;

    public QuickItemExtractor(ExtractService extractService) {
        this.extractService = extractService;

        producerQueue = new MessageBlockingQueue<>();
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        threadPool = new ThreadPoolExecutor(corePoolSize, corePoolSize, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2000));

    }

    public void extract() {
        logger.debug("开始" + getExtractorName() + "。。");
        long start = System.currentTimeMillis();

        // 抽取所有信息放到队列里
        new DefaultExtractTask().start();

        // 把队列里的信息消费保存
        doConsume();

        long end = System.currentTimeMillis();
        double cost = (end - start) / 1000;
        logger.debug("完成" + getExtractorName() + ",花费时间：" + cost + "秒");
    }

    private String getExtractorName() {
        return "$$" + Thread.currentThread().getId();
    }

    /**
     * 把队列里的 Message 消费再处理
     * 比如：保存
     */
    private void doConsume() {
        while (true) {
            // 2秒内取不到就退出
            Message message = null;
            try {
                message = producerQueue.poll(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (message == null) {
                break;
            }
            // 提交一个任务
            threadPool.submit(new MessageConsumerTask(message));
        }
    }

    /**
     * 默认抽取任务
     *
     * @author Asion
     */
    public class DefaultExtractTask extends Thread {
        public void run() {
            List<Message> messages = doExtract();
            System.out.println(messages);
        }
    }

    /**
     * 执行抽取操作
     *
     * 如：抽取页面url，页面各种信息
     *
     * @return 抽取的信息列表
     */
    private List<Message> doExtract() {
        logger.info("start to do extract message------>>>>>");
        // 抽取服务要自己实现
        List<Message> messages = getExtractService().queryAllMessages();
        if (messages == null) {
            return null;
        }
        logger.info("start to put message------>>>>>");
        for (Message message : messages) {
            producerQueue.offer(message);
        }
        return messages;
    }

    private ExtractService getExtractService() {
        return extractService;
    }
}