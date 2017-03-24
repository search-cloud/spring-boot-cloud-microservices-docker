package org.asion.base.ddd.domain.event;

import lombok.Data;
import org.asion.base.ddd.domain.annotations.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * base domain event
 *
 * @author Asion.
 * @since 2017/3/20.
 */
@Data
@DomainEvent
public class BaseDomainEvent<T> implements Serializable {
    /**
     * id
     */
    private String id = UUID.randomUUID().toString();

    /**
     * event type
     */
    protected String type;

    /**
     * event context
     */
    protected Map<String, Object> context;

    /**
     * source
     */
    protected transient Object source;

    /**
     * payload
     */
    protected T payload;

    /**
     * system time when the event happened.
     */
    private final long timestamp;

    public BaseDomainEvent() {
        this.timestamp = System.currentTimeMillis();
    }

    public BaseDomainEvent(Object source) {
        this.source = source;
        this.timestamp = System.currentTimeMillis();
    }

    public BaseDomainEvent(Object source, T payload) {
        this.source = source;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }

    public Object getAttribute(String name) {
        return context == null ? null : context.get(name);
    }

    public void setAttribute(String name, Object value) {
        if (context == null) {
            context = new HashMap<>();
        }
        this.context.put(name, value);
    }
}
