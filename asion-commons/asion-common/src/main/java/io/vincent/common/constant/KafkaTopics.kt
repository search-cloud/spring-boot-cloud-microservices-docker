package io.vincent.common.constant

/**
 * kafka topics constant
 *
 * @author Vincent.
 * @since 2017/06/21 16:24
 */
object KafkaTopics {

    /**
     * 账户创建
     */
    val ACCOUNT_REGISTER = "account-register"
    /**
     * 卖家创建
     */
    val SELLER_CREATE = "seller-create"
    /**
     * 雇员创建
     */
    val EMPLOYEE_CREATE = "employee-create"
    /**
     * 账户更新
     */
    val ACCOUNT_UPDATE = "account-update"

    /**
     * 账户冻结
     */
    val ACCOUNT_DISABLE = "account-disable"
    /**
     * 账户解冻
     */
    val ACCOUNT_ENABLE = "account-enable"

    /**
     * 买家冻结
     */
    val BUYER_DISABLE = "buyer-disable"
    /**
     * 买家解冻
     */
    val BUYER_ENABLE = "buyer-enable"

    /**
     * 卖家冻结
     */
    val SELLER_DISABLE = "seller-disable"
    /**
     * 卖家解冻
     */
    val SELLER_ENABLE = "seller-enable"

    /**
     * 雇员冻结
     */
    val EMPLOYEE_DISABLE = "employee-disable"
    /**
     * 雇员解冻
     */
    val EMPLOYEE_ENABLE = "employee-enable"

    /**
     * 商品下架
     */
    val ITEM_OFF_SHELVES = "item-off-shelves"

    /**
     * 商家上架
     */
    val ITEM_ON_SHELVES = "item-on-shelves"

    /**
     * 商品信息更新通知事件，该事件接收方装修
     */
    val ITEM_UPDATE_FOR_DECORATION = "update-for-decoration"

    /**
     * 销量增加
     */
    val ITEM_SOLD_NUMBER_INCREASE = "item-sold-number-increase"

    /**
     * 商品sku更新
     */
    val ITEM_SKU_UPDATE = "item_sku_update"

    /**
     * 营销商品点击事件
     */
    val PROMOTION_ITEM_CLICK = "promotion-item-click"
    /**
     * 活动下线
     */
    val ACTIVITY_OFF = "activity-off"
    /**
     * 优惠券到账
     */
    val COUPON_PROVIDE = "coupon_provide"
    /**
     * 红包到账
     */
    val REDBAG_PROVIDE = "red-bag-provide"
    /**
     * 商品浏览事件
     */
    val ITEM_BROWSE = "item-browse"

    val ACTIVITY_ITEM_BROWSE = "activity-item-browse"

    val PROMOTION_ITEM_OFF = "promotion-item-off"

    val PROMOTION_ITEM_ON_SALE = "promotion-item-onSale"

    val TRADE_SUCCESS = "trade-success"
    /**
     * 订单确认收货
     */
    val CONFIRM_ORDER = "confirm-order"

    /**
     * 订单发货  部分/全部
     */
    val ORDER_DELIVERY = "order-delivery"

    /**
     * 订单关闭
     */
    val ORDER_CLOSED = "order-closed"
    /**
     * 同意退货
     */
    val CRM_AGREE_RETURN = "crm-agree-return"
    /**
     * 同意退款
     */
    val CRM_AGREE_REFUND = "crm-agree-refund"
    /**
     * 拒绝退款
     */
    val CRM_SELLER_REFUSE_REFUND = "crm-seller-refuse-refund"

    /**
     * 卖家入驻审核成功
     */
    val SELLER_AUDIT_PASS = "seller-auditpass"
    /**
     * 卖家入驻审核失败
     */
    val SELLER_AUDIT_FAIL = "seller-auditfail"

    /**
     * 限时折扣
     */
    val LIMIT_DISCOUNT_MALL_PURCHASE = "limit-discount-mall-purchase"

    /**
     * 商家自定义类目商品关联表移除
     */
    val SELLER_CATEGORY_ITEM_REMOVE = "seller-category-item-remove"

    /**
     * 新建商家账户
     */
    val NEW_SELLER = "new-seller"

    /**
     * 新建inlife门店
     */
    val NEW_INLIFE_SELLER = "new-inlife-seller"

    /**
     * 营业执照到期
     */
    val SELLER_EXPIRATION_COMPANY = "seller-expiration-company"
    /**
     * 品牌授权到期
     */
    val SELLER_EXPIRATION_BRAND_AUTH = "seller-expiration-brand-auth"
    /**
     * 7天无理由退换货设置
     */
    val SEVEN_DAY_RETURN_CONFIG = "seven-day-return-config"
    /**
     * 活动进入预告中计划任务
     */
    val ACTIVITY_IN_THE_NOTICE_SCHEDULE = "activity-in-the-notice-schedule"
    /**
     * 活动开始计划任务
     */
    val ACTIVITY_START_SCHEDULE = "activity-start-schedule"
    /**
     * 活动结束计划任务
     */
    val ACTIVITY_END_SCHEDULE = "activity-end-schedule"

    /**
     * 活动报名开始
     */
    val ACTIVITY_ENROL_START_SCHEDULE = "activity_enrol_start_schedule"

    /**
     * 活动报名结束
     */
    val ACTIVITY_ENROL_END_SCHEDULE = "activity_enrol_end_schedule"

    /**
     * 拼团成功
     */
    val COLLECTIVE_SUCCESS = "collective_success"

    /**
     * 库存更新
     */
    val INVENTORY_UPDATED = "INVENTORY_UPDATED"

    /**
     * 库存新建
     */
    val INVENTORY_CREATED = "INVENTORY_CREATED"

    /**
     * 库存为零时
     */
    val INVENTORY_TO_ZERO = "INVENTORY_TO_ZERO"

    /**
     * 库存可销售量已更新
     */
    val INVENTORY_SALES_STOCK_UPDATED_TYPE = "INVENTORY_SALES_STOCK_UPDATED"

    /**
     * 库存可销售量已增加
     */
    val INVENTORY_SALES_STOCK_INCREASED = "INVENTORY_SALES_STOCK_INCREASED"

    /**
     * 库存可销售量已减少
     */
    val INVENTORY_SALES_STOCK_DECREASED = "INVENTORY_SALES_STOCK_DECREASED"

    /**
     * 仓库更新
     */
    val WAREHOUSE_UPDATED = "WAREHOUSE_UPDATED"

    /**
     * 仓库新建
     */
    val WAREHOUSE_CREATED = "WAREHOUSE_CREATED"

    /**
     * 仓库打开
     */
    val WAREHOUSE_OPENED = "WAREHOUSE_OPENED"

    /**
     * 仓库关闭
     */
    val WAREHOUSE_CLOSED = "WAREHOUSE_CLOSED"

    /**
     * 仓库删除
     */
    val WAREHOUSE_DELETED = "WAREHOUSE_DELETED"
    /**
     * 一分抽奖成功
     */
    val CENT_LUCK_SUCCESS = "cent_luck_success"
    /**
     * 小程序店买家登陆
     */
    val WECHAT_APP_STORE_BUYER_LOGIN = "wechat_app_store_buyer_login"
    /**
     * 课程增加观看次数
     */
    val EDU_COURSE_RESOURCES_ADD_VIEW_NUMBER = "edu_course_resources_add_view_number"
    /**
     * 课程增加支付次数
     */
    val EDU_COURSE_RESOURCES_ADD_PAY_NUMBER = "edu_course_resources_add_pay_number"

    /**
     * 全店满减活动进行中
     */
    val SHOP_MONEY_OFF_RUN__SCHEDULE = "shop_money_off_run__schedule"

    /**
     * 全店满减活动结束
     */
    val SHOP_MONEY_OFF_FINISH_SCHEDULE = "shop_money_off_finish_schedule"
}
