--
-- 命名规范:
--  组织名称_模块名称_表名称
--  如:
--  asion_sample_sample

-- 例子主表:
DROP TABLE IF EXISTS asion_sample_sample;
CREATE TABLE asion_sample_sample (
  id      BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
  text    VARCHAR(255) COMMENT 'text',
  summary VARCHAR(64) COMMENT 'summary',
  created_at DATETIME COMMENT '创建时间',
  updated_at DATETIME COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '商品表';
