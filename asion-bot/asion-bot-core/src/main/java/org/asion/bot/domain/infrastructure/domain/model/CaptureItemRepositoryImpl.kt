package org.asion.bot.domain.infrastructure.domain.model

import org.asion.bot.CaptureItem
import org.asion.bot.domain.infrastructure.domain.model.mapper.CaptureItemMapper
import org.asion.bot.domain.model.CaptureItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import kotlin.streams.toList

/**
 *
 * @author Asion.
 * @since 2018/5/3.
 */
@Repository
class CaptureItemRepositoryImpl @Autowired
constructor(private var captureItemMapper: CaptureItemMapper) : CaptureItemRepository {

//    val insertSql = "insert into seek_item(origin_id, category_name, brand_name, country, item_name, qualification, market_price, sale_price, sale_number, detail_link, created_at, updated_at) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())"
//    val updateSql = "update seek_item set origin_id=?, category_name=?, brand_name=?, country=?, item_name=?, qualification=?, market_price=?, sale_price=?, sale_number=?, detail_link=?, updated_at=now() where id=?"

    override fun <S : CaptureItem> save(entity: S): S {
        when {
            entity.id == null -> captureItemMapper.insert(entity)
            else -> captureItemMapper.update(entity)
        }
        return entity
    }

    override fun <S : CaptureItem> save(entities: List<S>): List<S> = entities.stream().map<S>({ this.save(it) }).toList()

    override fun findOne(id: Long): CaptureItem? = captureItemMapper.findOne(id)

    override fun findList(ids: List<Long>): List<CaptureItem> =
            ids.stream().map { this.findOne(it) }.filter { it != null }.toList().requireNoNulls()

    override fun exists(id: Long): Boolean = captureItemMapper.count(id) > 0

    override fun delete(id: Long) = captureItemMapper.delete(id) > 0

    override fun delete(entity: CaptureItem) = captureItemMapper.delete(entity.id!!) > 0

    override fun findAll(): List<CaptureItem> {
        return captureItemMapper.findAll()
    }

    override fun saveCaptureItems(itemList: List<CaptureItem>): List<Long> {
        itemList.forEach {
            it.id = save(it).id
        }
        return itemList.mapNotNull { it.id }.toList()
    }
}