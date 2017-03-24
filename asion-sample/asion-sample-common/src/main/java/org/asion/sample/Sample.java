package org.asion.sample;

import org.asion.base.ddd.domain.BaseDomainEntity;

import java.util.Calendar;

/**
 * @author Asion.
 * @since 16/4/29.
 */
public class Sample implements BaseDomainEntity<Long> {

    private Long id;

    private String text;

    private String summary;

    private Calendar createdAt = Calendar.getInstance();

    private Calendar updatedAt = Calendar.getInstance();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Calendar updatedAt) {
        this.updatedAt = updatedAt;
    }
}
