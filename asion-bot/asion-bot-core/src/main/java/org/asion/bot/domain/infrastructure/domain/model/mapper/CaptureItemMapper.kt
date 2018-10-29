package org.asion.bot.domain.infrastructure.domain.model.mapper

import org.apache.ibatis.annotations.*
import org.apache.ibatis.mapping.StatementType
import org.asion.bot.CaptureItem

/**
 * @author Asion.
 * @since 2017/10/9.
 */
interface CaptureItemMapper {

    /**
     * 新增
     *
     * @param captureItem 实体
     * @return 影响的行数
     */
    @Insert("insert into item(origin_id, source, source_name, category_name, brand_name, country, item_name, qualification, market_price, sale_price, sale_number, detail_link, created_at, updated_at, index, grab) values (#{item.originId}, #{item.source}, #{item.sourceName}, #{item.categoryName}, #{item.brandName}, #{item.country}, #{item.itemName}, #{item.qualification}, #{item.marketPrice}, #{item.salePrice}, #{item.saleNumber}, #{item.detailLink}, now(), now(), #{item.index}, #{item.grab})")
    @SelectKey(before=false, keyProperty="item.id", resultType=Long::class, statementType=StatementType.STATEMENT, statement=["SELECT LAST_INSERT_ID() AS id"])
    fun insert(@Param("captureItem") captureItem: CaptureItem): Int

    /**
     * 更新
     *
     * @param captureItem 实体
     * @return 影响的行数
     */
    @Update("update item set origin_id=#{item.originId}, source=#{item.source}, source_name=#{item.sourceName}, category_name=#{item.categoryName}, brand_name=#{item.brandName}, country=#{item.country}, item_name=#{item.itemName}, qualification=#{item.qualification}, market_price=#{item.marketPrice}, sale_price=#{item.salePrice}, sale_number=#{item.saleNumber}, detail_link=#{item.detailLink}, updated_at=now(), index=#{item.index}, grab=#{item.grab} where id=#{item.id, jdbcType=BIGINT}")
    fun update(@Param("captureItem") captureItem: CaptureItem): Int

    /**
     * 根据主键返回
     *
     * @param id 主键
     * @return demo对象
     */
    @Select("select ${SELECT_COLUMNS} from seek_item where id=#{id, jdbcType=BIGINT}")
    fun findOne(@Param("id") id: Long): CaptureItem?

    /**
     * 根据id返回对象是否存在
     *
     * @param id must not be null.
     * @return true if an entity with the given id exists, false otherwise
     */
    @Select("select count(id) from seek_item where id=#{id, jdbcType=BIGINT}")
    fun count(@Param("id") id: Long): Int

    /**
     * 根据主键删除
     *
     * @param id 主键
     */
    @Delete("delete from seek_item where id=#{id, jdbcType=BIGINT}")
    fun delete(@Param("id") id: Long): Int

    companion object {
        const val SELECT_COLUMNS = " id, origin_id as originId, source, source_name as sourceName, category_name as categoryName, brand_name as brandName, country, item_name as itemName, qualification, market_price as marketPrice, sale_price as salePrice, sale_number as saleNumber, detail_link as detailLink, created_at as createdAt, updated_at as updatedAt, index, grab "
    }

    /**
     * 返回所有的seekerCaptureItem
     */
    @Select("select ${SELECT_COLUMNS} from seek_item")
    fun findAll(): List<CaptureItem>

}
