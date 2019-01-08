--
-- 命名规范:
--  组织名称_模块名称_表名称
--  如:
--  asion_sample_sample

-- 例子主表:
# ================================================================================
# account DDL:
# ================================================================================
DROP TABLE IF EXISTS account;
CREATE TABLE account (
    id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    code VARCHAR(64) DEFAULT NULL COMMENT 'code',
    username VARCHAR(64) DEFAULT NULL COMMENT '登陆账号名称，保证唯一，创建后不可修改',
    nick_name VARCHAR(64) DEFAULT NULL COMMENT '昵称',
    password VARCHAR(64) DEFAULT NULL COMMENT '密码',
    used_password VARCHAR(64) DEFAULT NULL COMMENT '曾用密码',
    mobile VARCHAR(64) DEFAULT NULL COMMENT '手机号',
    email VARCHAR(64) DEFAULT NULL COMMENT '邮件',
    wechat_id VARCHAR(64) DEFAULT NULL COMMENT '微信id',
    enabled TINYINT(1) DEFAULT 0 COMMENT '账号是否可用',
    parent_id BIGINT(20) DEFAULT NULL COMMENT '父账号id',
    status INT(8) DEFAULT NULL COMMENT '状态',
    last_login_at DATETIME NOT NULL DEFAULT now() COMMENT '最后登陆时间',
    operation_error_count INT(8) DEFAULT NULL COMMENT '操作错误次数',
    created_at DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT now() COMMENT '更新时间'
 ) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='账号信息';

