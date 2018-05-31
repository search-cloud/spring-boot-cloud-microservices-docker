package org.asion.bot.selenium.processor.extractor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Asion.
 * @since 2018/5/17.
 */
public class ExtractServiceImpl implements ExtractService {
    @Override
    public List<Message> queryAllMessages() {
        List<Message> messages = new ArrayList<>(1000);

        for (int i = 0; i < 1000; i++) {
            messages.add(new ItemMessage(i));
        }

        return messages;
    }
}
