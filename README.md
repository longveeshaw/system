## 演示
<http://system.basicfu.com>

## 接口文档
[文档地址]()

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
