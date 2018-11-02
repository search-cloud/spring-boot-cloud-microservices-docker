# 公共项目 asion-common

主要是放一些多个项目中公用的包，且不需要引用第三方包的工具。

## 基础配置

所有的工具或相关类，都需要先在pom.xml中引入以下依赖：
```xml
    <dependency>
        <groupId>io.vincent.commons</groupId>
        <artifactId>asion-common</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
```

## MathUtil 数学运算工具

使用参考：[MathUtil](src/main/java/com/asion/common/MathUtils.java)类API。

## Page 分页返回值

[Page](src/main/java/com/asion/common/Page.java)
分页插件查询好结果集后的返回值。

eg.)
```
    PageHelper.startPage(pageNumber, pageSize);
    List<Item> itemList = itemMapper.selectList(item.getCategoryId(), item.getSellerAccountId(), item.getBrandId(),
            item.getStatusType(), item.getStatus(), item.getName(), item.getArticleNumber(), item.getSoldNum(), item.getFavoritesNum());
    PageInfo pageInfo = new PageInfo<>(itemList);
    Page<Item> page = new Page<>();
    BeanUtils.copyProperties(pageInfo, page);
    return page;
```

## RegexUtils 一些规则校验工具

使用参考：[RegexUtils](src/main/java/com/asion/common/RegexUtils.java)类API。

## IdentityUtils 13位UUID生成类

使用参考：[UniqueIdentity](src/main/java/com/asion/common/IdentityUtils.java)类API。

## io.vincent.common.vo.* 包中的类说明

[Response](src/main/java/com/asion/common/vo/Response.java)
用作 Restful API 返回值

使用例子，方式一：
```
val result = Response()
val xx = xxManager.findOne(id)
if (xx == null) {
    result.success = SuccessFlag.FALSE.value
    result.code = 41_003404
    result.message = errors.message(41_003404, id)
    return result
}

result.addData("xx", xx)
return result
```
方式二：
```
val xx = xxManager.findOne(id!!) ?:
          return Response.builder().success(SuccessFlag.FALSE.value)
                 .code(41_003404).message(errors.message(41_003404, id)).build()
                 
Response.builder().addData("xx", xx).build()
```

以下两个类暂时不需要：

[Data](src/main/java/com/asion/common/vo/Data.java)
[Message](src/main/java/com/asion/common/vo/Message.java)

## 业务异常 BizException

待续……
