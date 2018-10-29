package org.asion.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.asion.base.ddd.domain.BaseDomainEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author Asion.
 * @since 16/4/29.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "item", type = "item", shards = 2, replicas = 2)
public class Item implements BaseDomainEntity<Long> {

    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 商品名称
     */
    @Field(type = FieldType.Text)
    private String name;

    /**
     * 商品简介
     */
    @Field(type = FieldType.Text)
    private String brief;

    /**
     * 最终销售价
     */
    @Field(type = FieldType.Float)
    private Double salePrice;

    /**
     * 销售价的最低价
     */
    @Field(type = FieldType.Float)
    private Double saleLowPrice;

    /**
     * 销售价的最高价
     */
    @Field(type = FieldType.Float)
    private Double saleHighPrice;

    @Field(type = FieldType.Date)
    private Date createdAt;

    @Field(type = FieldType.Date)
    private Date updatedAt;

    public Item(Long id, String name, String brief, Double salePrice) {
        this.id = id;
        this.name = name;
        this.brief = brief;
        this.salePrice = salePrice;
    }
}
