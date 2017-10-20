-- 1 编码业务类型表
DROP TABLE IF EXISTS `base_code_biz_type`;
CREATE TABLE `base_code_biz_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `type_level` tinyint(1) NOT NULL DEFAULT 1 COMMENT '规则级别（1平台；2通用）',
  `system_name` varchar(20) NOT NULL DEFAULT '' COMMENT '所属系统',
  `biz_name` varchar(20) NOT NULL DEFAULT '' COMMENT '业务名称',
  `biz_code` varchar(50) NOT NULL DEFAULT '' COMMENT '业务编码',
  `state` tinyint(1) unsigned NOT NULL DEFAULT 2 COMMENT '状态（1未启用，2启用，3停用，4删除）',
  `create_user` int(11) unsigned DEFAULT 0 COMMENT '创建人',
  `create_time` bigint(20) unsigned DEFAULT 0 COMMENT '创建时间',
  `update_user` int(11) unsigned DEFAULT 0 COMMENT '更新人',
  `update_time` bigint(20) unsigned DEFAULT 0 COMMENT '更新时间',
  UNIQUE KEY `biz_code_uk_idx` (`biz_code`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT '编码业务类型表';

insert into base_code_biz_type
(id,type_level, system_name, biz_name, biz_code, state, create_user, create_time)
values
(1,1,'BASE','编码规则编码','BASE_CODE_RULE_CODE',2,1,unix_timestamp(now()));


-- 2 编码业务类型元数据
DROP TABLE IF EXISTS `base_code_biz_type_metadata`;
CREATE TABLE `base_code_biz_type_metadata` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `type_id` int(11) unsigned NOT NULL DEFAULT 0 COMMENT '实体ID',
  `metadata_name` varchar(40) NOT NULL DEFAULT '' COMMENT '实体属性',
  `metadata_show` varchar(20) NOT NULL DEFAULT '' COMMENT '实体域名',
  `create_user` int(11) unsigned DEFAULT 0 COMMENT '创建人',
  `create_time` bigint(20) unsigned DEFAULT 0 COMMENT '创建时间',
  `update_user` int(11) unsigned DEFAULT 0 COMMENT '更新人',
  `update_time` bigint(20) unsigned DEFAULT 0 COMMENT '更新时间',
  UNIQUE KEY `type_name_uk_idx` (`type_id`,`metadata_name`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT '编码业务类型元数据';

-- 3 编码规则表
DROP TABLE IF EXISTS `base_code_rule`;
CREATE TABLE `base_code_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `rule_level` tinyint(1) NOT NULL DEFAULT 1 COMMENT '规则级别（1全局；2集团）',
  `group_id` int(11) NOT NULL DEFAULT 1 COMMENT '隶属集团',
  `biz_code` varchar(50) NOT NULL DEFAULT '' COMMENT '业务编码',
  `rule_code` varchar(12) NOT NULL DEFAULT '' COMMENT '规则编码',
  `rule_name` varchar(50) NOT NULL DEFAULT '' COMMENT '规则名称',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态（1未启用，2启用，3停用，4删除）',
  `code_way` tinyint(1) unsigned NOT NULL DEFAULT '2' COMMENT ' 编码方式（1保存前编码；2保存后编码）',
  `break_code` tinyint(1) NOT NULL DEFAULT 0 COMMENT '断码补码（0否；1是）',
  `zero_reason` tinyint(1) NOT NULL DEFAULT 1 COMMENT '归零依据（1全局；2集团；3组织）',
  `defaulted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认规则（0 否 1 是）',
  `editable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '手动修改（0 否 1是）',
  `cover` tinyint(1) NOT NULL DEFAULT 0 COMMENT '补位（0 否 1 是）',
  `time_format` varchar(8) NOT NULL DEFAULT 'yyyyMMdd' COMMENT '时间格式',
  `total_lenght` int(11) NOT NULL DEFAULT '0' COMMENT '编码长度',
  `create_user` int(11) unsigned DEFAULT 0 COMMENT '创建人',
  `create_time` bigint(20) unsigned DEFAULT 0 COMMENT '创建时间',
  `update_user` int(11) unsigned DEFAULT 0 COMMENT '更新人',
  `update_time` bigint(20) unsigned DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_index_rule_code` (`rule_code`),
  UNIQUE KEY `unique_index_rule_name` (`rule_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '编码规则表';

insert into base_code_rule
(id, level, group_id, biz_code, rule_code, rule_name, code_way, break_code, zero_reason, defaulted, editable, cover, time_format, total_lenght, state, create_user, create_time)
values
(1,1,1,'CODE_RULE_CODE','GZBM00000000','编码规则编码',1,0,1,1,0,1,'yyyyMMdd',12,2,1,unix_timestamp(now()));

insert into base_code_rule
(id, level, group_id, biz_code, rule_code, rule_name, code_way, break_code, zero_reason, defaulted, editable, cover, time_format, total_lenght, state, create_user, create_time)
values
(2,1,1,'DEFAULT_CODE','GZBM00000001','平台初始化',1,0,1,1,0,1,'yyyyMMdd',14,2,1,unix_timestamp(now()));

-- 4 规则项表
DROP TABLE IF EXISTS `base_code_item`;
CREATE TABLE `base_code_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `rule_id` int(11) NOT NULL DEFAULT 0 COMMENT '编码规则id',
  `sequence` tinyint(1) NOT NULL DEFAULT 1 COMMENT '段次序',
  `section_type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '段类型(1常量;2字符串;3时间类型;4流水号)',
  `section_value` varchar(255) DEFAULT '' COMMENT '段值(时间类型：1系统时间；2单据时间；3创建时间)',
  `section_length` tinyint(1) NOT NULL DEFAULT 0 COMMENT '段长度',
  `is_serial` tinyint(1) NOT NULL DEFAULT 0 COMMENT '流水依据（0 否;1 是）',
  `serial_type` tinyint(1) DEFAULT 0 COMMENT '流水依据（0:不流水1:按日流水2:按月流水3:按年流水4:按字符串流水）',
  `cover_way` tinyint(1) NOT NULL DEFAULT 1 COMMENT '补位方式（只有字符串类型时：1左补位；2右补位;3不补位）',
  `cover_char` char(1) NOT NULL DEFAULT '0' COMMENT '补位字符',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态（1未启用，2启用，3停用，4删除）',
  `create_user` int(11) unsigned DEFAULT 0 COMMENT '创建人',
  `create_time` bigint(20) unsigned DEFAULT 0 COMMENT '创建时间',
  `update_user` int(11) unsigned DEFAULT 0 COMMENT '更新人',
  `update_time` bigint(20) unsigned DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT '规则项表';

insert into base_code_item
(id, code_id, sequence, section_type,section_value, section_length, is_serial, serial_type, cover_way,cover_char, state, create_user, create_time)
values
(1,1,1,1,'GZBM',4,0,0,3,'0',2,1,unix_timestamp(now()));

insert into base_code_item
(id, code_id, sequence, section_type,section_value, section_length, is_serial, serial_type, cover_way,cover_char, state, create_user, create_time)
values
(2,1,2,4,'99999999',8,0,0,1,'0',2,1,unix_timestamp(now()));


insert into base_code_item
(id, code_id, sequence, section_type,section_value, section_length, is_serial, serial_type, cover_way,cover_char, state, create_user, create_time)
values
(3,2,1,3,'1',8,0,1,3,'0',2,1,unix_timestamp(now()));

insert into base_code_item
(id, code_id, sequence, section_type,section_value, section_length, is_serial, serial_type, cover_way,cover_char, state, create_user, create_time)
values
(4,2,2,4,'999999',6,0,0,1,'0',2,1,unix_timestamp(now()));


-- 5 编码规则与组织关系表
DROP TABLE IF EXISTS `base_code_rule_item_relation`;
CREATE TABLE `base_code_rule_item_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `rule_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '规则ID',
  `group_id` int(11) NOT NULL DEFAULT 0 COMMENT '集团ID',
  `org_id` int(11) NOT NULL DEFAULT 0 COMMENT '业务单元ID',
  `biz_code` varchar(50) NOT NULL DEFAULT '' COMMENT '业务编码',
  `defaulted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认规则（0 否 1 是）',
  `step_size` int(11) NOT NULL DEFAULT 100000 COMMENT '缓存步长',
  `step_num` int(11) NOT NULL DEFAULT 1 COMMENT '缓存步数',
  `create_user` int(11) unsigned DEFAULT 0 COMMENT '创建人',
  `create_time` bigint(20) unsigned DEFAULT 0 COMMENT '创建时间',
  `update_user` int(11) unsigned DEFAULT 0 COMMENT '更新人',
  `update_time` bigint(20) unsigned DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '编码规则与组织关系表';

insert into base_code_serial_info (id,rule_id,group_id,org_id,biz_code,step_size,step_num,create_time)
values
(1,1,0,0,0,10000,1,unix_timestamp(now()));
