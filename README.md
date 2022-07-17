# 工程教育认证系统后端 EEASBackend

Engineering Education Accreditation System Backend

将为高校实现完整的学生成绩评定以及回访功能。

[该仓库](https://github.com/AkagiYui/EEASBackend)仅为后端服务，前端服务请前往[EEASFrontend](https://github.com/AkagiYui/EEASFronted)

> 注意！这只是个人学生学习Java的作品，谨慎用于生产环境！
> 
> 由于这是本人的第一个 Java 项目，各种不规范请**指出**，以帮助我改进，感谢！

## 功能详情 [Functions](wiki/index.md)

## 更新日志 [Changelog](Changelog.md)

## 注意事项 Tips

该项目尚未成熟！

`master`分支仍处于开发阶段，请勿在生产环境使用！

## 开发相关 Development

### 使用技术 TechnologyStack

Java版本: [liberica 17.0.3.1](https://bell-sw.com/) [下载地址](https://bell-sw.com/pages/downloads/#/java-17-lts)

构建工具: [Gradle 7.4.1](https://gradle.org/) [下载地址](https://gradle.org/next-steps/?version=7.4.2&format=bin)

关系型数据库: [MySQL 8.0.29](https://www.mysql.com/) [下载地址](https://dev.mysql.com/downloads/mysql/8.0.29.html)

#### Java库 Java Library

- [Spring Boot 2.7.1](https://spring.io/projects/spring-boot) Restful API 基础框架
- [MySQLConnector](https://dev.mysql.com/doc/connector-j/8.0/en/) MySQL 驱动
- [MyBatis](https://blog.mybatis.org/) 数据库ORM框架
- [MyBatis-Plus 3.5.2](https://baomidou.com/) MyBatis增强工具
- [springdoc-openapi 1.6.9](https://springdoc.org/) API文档生成工具
- [Lombok](https://projectlombok.org/) 注解工具
- [JetbrainsJavaAnnotations 23.0.0](https://github.com/JetBrains/java-annotations) 注解工具
- [Hutool 5.8.4](https://hutool.cn/) 工具类
- [Spring Boot Test](https://spring.io/guides/gs/testing-web/) 测试工具
- [java-jwt](https://github.com/auth0/java-jwt) JWT工具

### 待办事项 Todo

- [ ] 补充API文档注解
- [ ] 持久化配置文件
- [ ] 迁移至Gradle
- [ ] 更新jwt库
- [ ] 使用Redis缓存
