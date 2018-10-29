package org.asion.bot.selenium.processor.executor;

import java.io.Serializable;

public class Message implements Serializable {
    private Integer id;
    private String name;
    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Message(Integer id, String name, String source) {
        this.id = id;
        this.name = name;
        this.source = source;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}