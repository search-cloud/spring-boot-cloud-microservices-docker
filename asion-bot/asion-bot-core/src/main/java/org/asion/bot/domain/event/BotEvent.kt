package org.asion.bot.domain.event

import org.asion.base.ddd.domain.annotations.DomainEvent
import org.asion.base.ddd.domain.event.BaseDomainEvent
import org.asion.bot.CaptureItem

/**
 * @author Asion.
 * @since 2017/1/20.
 */
@DomainEvent
class BotEvent(type: String, captureItem: CaptureItem) : BaseDomainEvent<CaptureItem>() {

    init {
        //        setType(type);
        setPayload(captureItem)
    }

    companion object {
        var CREATED_TYPE = "created"
        var BLOCKED_TYPE = "blocked"
        var DELETED_TYPE = "DELETED"
    }
}
