package org.asion.bot.service

import com.alibaba.dubbo.config.annotation.DubboService
import org.asion.bot.CaptureItem
import org.asion.bot.CaptureItemManger
import org.asion.bot.domain.model.CaptureItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author Asion.
 * @since 16/5/29.
 */
@Component
@DubboService(interfaceClass = CaptureItemManger::class)
class CaptureItemManagerImpl @Autowired
constructor(private val captureItemRepository: CaptureItemRepository) : CaptureItemManger {

    override fun save(itemList: List<CaptureItem>): List<Long> {
        return captureItemRepository.saveCaptureItems(itemList)
    }

    override fun save(captureItem: CaptureItem): Long {
        return captureItemRepository.save(captureItem).id
    }
}
