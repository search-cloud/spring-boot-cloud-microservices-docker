--
-- 命名规范:
--  组织名称_模块名称_表名称
--  如:
--  asion_sample_sample

-- 例子主表:
DROP TABLE IF EXISTS asion_account;
CREATE TABLE asion_account (
  id      BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
  code    VARCHAR(64) COMMENT 'code',
  name    VARCHAR(64) COMMENT 'name',
  password    VARCHAR(64) COMMENT 'password',
  nick_name   VARCHAR(64) COMMENT 'nick_name',
  mobile    VARCHAR(64) COMMENT 'mobile',
  email    VARCHAR(64) COMMENT 'email',
  enabled    BIT(1) COMMENT 'enabled',
  created_at DATETIME COMMENT '创建时间',
  updated_at DATETIME COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '登录账户信息';
