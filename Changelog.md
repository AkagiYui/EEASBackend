## 0.3.1

- 移除了Maven配置，项目使用Gradle构建
- 移除了GitHub Actions的Maven构建任务
- 完善了README文档
- `com.auth0:java-jwt`库升级为`3.19.2`版本
- `用户注册`接口修改为`新增用户`接口
- 新增了自定义静态工具类

## 0.3.0

- 添加 Apache 2.0 许可证
- 添加 Gradle 配置文件
- 项目重命名为EEASBackend

## 0.2.3

- 添加配置文件注释
- 用户添加超级管理员选项(拥有所有权限)
- 修复了token解析错误的问题
- java版本升级至17

## 0.2.2

- api 文档说明更新
- `/user/login`用户名/密码错误时不再产生错误
- 添加用户禁用功能

## 0.2.1

- 添加 log 表
- 新增API 获取日志
- 升级为 swagger3

## 0.2.0

- 合并 survey 项目
- 添加 HTTP 请求日志提示
- `/user/overview`接口移至`/survey/overview`
- 支持 GitHub Actions 自动构建
- 自动同步代码到 Gitee

## 0.1.0

发布日期：2022-07-12

- 创建项目
- 支持了业务异常捕获
- 支持了接口不存在异常捕获
- 支持了 Jenkins 自动构建部署
