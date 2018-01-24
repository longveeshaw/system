- 通知配置（邮件、短信）
- 通知开关（哪些需要通知）
- 层级字典配置（使用左右值算法）
- 读写分离配置在配置文件中设置不需要修改代码

文章管理
# 系统管理
#   系统日志
#   数据字典

# 监控管理
#   消息接受者
# 消息配置

# CREATE TABLE `msg_receiver` (
#   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
#   `username` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名',
#   `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
#   `mobile` varchar(30) NOT NULL DEFAULT '' COMMENT '手机号',
#   `cdate` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
#   `udate` int(11) NOT NULL DEFAULT '0' COMMENT '更新日期',
#   PRIMARY KEY (`id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息接收者表';


## 演示
<http://system.basicfu.com>

## 接口文档
[文档地址](https://basicfu.github.io/system)

## 为什么做这个项目

## 特征

- 统一响应结果封装及生成工具
- 统一异常处理

## 使用

1.`git clone https://github.com/basicfu/system.git`

2.导入`system/doc/system.sql`数据库表和`system/doc/system_init.sql`数据

3.修改`application-dev.yml`中的`mysql`和`redis`连接信息

4.`cd  system && mvn package`

5.`java -jar target/system-1.0.0.jar`
