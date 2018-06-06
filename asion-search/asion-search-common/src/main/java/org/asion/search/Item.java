package org.asion.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.asion.base.ddd.domain.BaseDomainEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Asion.
 * @since 16/4/29.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "item", type = "item", shards = 2, replicas = 2, refreshInterval = "-1")
public class Item implements BaseDomainEntity<Long> {

    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 商品名称
     */
    @Field(type = FieldType.keyword)
    private String name;

    /**
     * 商品简介
     */
    @Field(type = FieldType.keyword)
    private String brief;

    /**
     * 最终销售价
     */
    private BigDecimal salePrice;

    /**
     * 销售价的最低价
     */
    private BigDecimal saleLowPrice;

    /**
     * 销售价的最高价
     */
    private BigDecimal saleHighPrice;

    @Field
    private Date createdAt;

    @Field
    private Date updatedAt;

}
