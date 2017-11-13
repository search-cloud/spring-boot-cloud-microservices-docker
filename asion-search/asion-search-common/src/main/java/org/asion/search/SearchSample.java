package org.asion.search;

import org.asion.base.ddd.domain.BaseDomainEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Calendar;

/**
 * @author Asion.
 * @since 16/4/29.
 */
@Document(indexName = "search_sample", type = "search_sample", shards = 1, replicas = 0, refreshInterval = "-1")
public class SearchSample implements BaseDomainEntity<Long> {

    @Id
    private Long id;

    @Field(type = FieldType.String)
    private String text;

    @Field(type = FieldType.String)
//    @Transient
    private String summary;

    @Field
    private Calendar createdAt = Calendar.getInstance();

    @Field
    private Calendar updatedAt = Calendar.getInstance();

    public SearchSample() {}

    public SearchSample(Long id, String text, String summary) {
        this.id = id;
        this.text = text;
        this.summary = summary;
    }

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
