package org.asion.bot.selenium.processor.extractor;

import lombok.ToString;

/**
 * @author Asion.
 * @since 2018/5/16.
 */
@ToString
public class ItemMessage implements Message {

    private Integer id = 0;
    private String name = "Message-" + id;

    public ItemMessage(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return "Message-" + id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


}
