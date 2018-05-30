package org.asion.bot;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Asion.
 * @since 2018/5/5.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptureItem implements Serializable {
    Long id;

    /**
     * 原始的id
     */
    Long originId;

    /**
     * 来源
     */
    Integer source;

    /**
     * 来源名称
     */
    String sourceName;

    /**
     * 三级类目，也是叶子节点
     */
    String categoryName;

    /**
     * 品牌名称
     */
    String brandName;

    /**
     * 生产国家
     */
    String country;

    /**
     * 商品名称
     */
    String itemName;

    /**
     * 包装规格
     */
    String qualification;

    /**
     * 市场价
     */
    String marketPrice;

    /**
     * 当前销售价
     */
    String salePrice;

    /**
     * 销量
     */
    String saleNumber;

    /**
     * 商品链接
     */
    String detailLink;

    Date createdAt;

    Date updatedAt;

    int index;

    int grab;

    @Getter
    @AllArgsConstructor
    public enum Source {
        TAOBAO(1, "淘宝"), TMALL(2, "天猫"), JD(3, "京东"), KAOLA(4, "考拉");

        private int sourceId;
        private String name;
    }
}
