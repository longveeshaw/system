drop database if exists system;
create database system default charset utf8 collate utf8_general_ci;
USE system;
create table menu
(
  id bigint auto_increment comment '主键'
    primary key,
  pid bigint not null comment '父级ID',
  name varchar(32) not null comment '菜单名',
  url varchar(255) null comment '菜单URL',
  sort int default '0' not null comment '菜单顺序',
  icon varchar(255) null comment '菜单图标'
)
  comment '菜单表' engine=InnoDB
;

create table permission
(
  id bigint auto_increment comment '权限ID'
    primary key,
  name varchar(32) not null comment '权限名'
)
  comment '权限表' engine=InnoDB
;

create table permission_resource
(
  permission_id bigint not null comment '权限ID',
  resource_id bigint not null comment '资源ID',
  primary key (permission_id, resource_id)
)
  comment '权限资源表' engine=InnoDB
;

create table resource
(
  id bigint auto_increment comment '资源ID'
    primary key,
  url varchar(100) not null comment '资源URL',
  name varchar(100) not null comment '资源名',
  constraint uk_resourceurl
  unique (url)
)
  comment '资源表' engine=InnoDB
;

create table role
(
  id bigint auto_increment comment '主键'
    primary key,
  name varchar(32) not null comment '角色名'
)
  comment '角色表' engine=InnoDB
;

create table role_menu
(
  role_id bigint not null comment '角色ID',
  menu_id bigint not null comment '菜单ID',
  primary key (role_id, menu_id)
)
  comment '角色菜单关系表' engine=InnoDB
;

create table role_permission
(
  role_id bigint not null comment '角色ID',
  permission_id bigint not null comment '权限ID',
  primary key (role_id, permission_id)
)
  comment '角色权限表' engine=InnoDB
;

create table user
(
  id bigint auto_increment comment '主键'
    primary key,
  username varchar(40) not null comment '用户名',
  nickname varchar(32) not null comment '真实姓名',
  password varchar(64) not null comment '登录密码',
  salt varchar(64) not null comment '盐',
  mobile varchar(22) not null comment '手机号码',
  cdate int default '0' not null comment '注册时间',
  udate int default '0' not null comment '最后一次修改时间',
  status tinyint default '0' not null comment '0正常,1删除',
  constraint uk_username
  unique (username),
  constraint uk_mobile
  unique (mobile)
)
  comment '用户表' engine=InnoDB
;

create table user_role
(
  user_id bigint not null comment '用户ID',
  role_id bigint not null comment '角色ID',
  primary key (user_id, role_id)
)
  comment '用户角色表' engine=InnoDB
;
create table config
(
  id bigint auto_increment comment '主键'
    primary key,
  name varchar(255) not null comment '配置名',
  value varchar(255) not null comment '配置值',
  primary key (user_id, role_id)
)
  comment '系统配置表' engine=InnoDB
;

CREATE TABLE dict (
  id bigint auto_increment comment '主键'
    primary key,
  name varchar(255) NOT NULL DEFAULT '' COMMENT '字典名',
  type varchar(255) NOT NULL DEFAULT '' COMMENT '字典类型',
  lft bigint NOT NULL DEFAULT '0' COMMENT '左节点',
  rgt bigint NOT NULL DEFAULT '0' COMMENT '右节点',
  lvl int NOT NULL DEFAULT '0' COMMENT '节点层级',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1是 0否',
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  KEY `lft` (`lft`),
  KEY `rgt` (`rgt`),
  KEY `status` (`status`)
)
  comment '字典表' engine=InnoDB;