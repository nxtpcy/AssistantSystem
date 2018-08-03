# XX管理系统
[![Build Status](https://travis-ci.org/shuzheng/zheng.svg?branch=master)](https://travis-ci.org/shuzheng/zheng)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/shuzheng/zheng/pulls)

## 演示地址

演示地址： [http://47.100.112.86:8080/help-front520/login.html](http://47.100.112.86:8080/help-front520/login.html)
用户角色分别为：
管理员： admin             密码：111111
部门管理员： weigu       密码：111111
助管：201521060000    密码：111111

## 项目介绍

　　`xx管理系统`基于Spring+SpringMVC+Mybatis+Shiro+easyUI架构，提供服务模块包括：权限管理，系统管理，设岗管理，助管管理，月考核管理，问卷调查，日志管理等。
- 权限管理：不同的角色拥有不同的权限功能。
- 系统管理：包括设置部门，查看角色信息等一系列功能。
- 设岗管理：设置助管岗位和岗位数量等。
- 助管管理：包含助管申请，助管审核等。
- 月考核管理：包含补助金额发放，考核表导出等。
- 问卷调查： 设置问卷和问卷填写记录等。
- 日志管理： 利用spring aop和log4j对相应的操作进行记录。

### 组织结构

``` lua
help_front                 --前端相关文件
├── css                     -- 使用的CSS文件
├── js                       -- 自定义js脚本文件
├── jquery-easyui    -- jquery-easyui框架相关文件
├── images              -- 使用的图片
├── ...                       -- 未完待续。。。

cn.edu.XXXX.smgt    --后台相关资料
src/main/resources  --配置文件
├── config_remote.properties      -- 数据库相关配置
├── ehcache.xml                           -- memcache配置文件
├── deptExcel.xsl                          -- 导出审核数据时使用的表格模板
├── log4j.properties                     -- log4j配置
├── spring.xml                              -- spring配置
├── spring-shiro.xml                     -- spring-shiro配置
├── spring-mvc.xml                      -- springMVC配置
├── spring-mybatis.xml                -- spring-mybatis配置
pom.xml                                         --Maven配置

src/main/java   --java类
├── annotation                             --自定义注解
├── aspect                                    -- aop 切面
├── common                                -- ssm框架公共模块
├── controller                               -- springMVC之Controller
├── dao                                        -- mybatis对应的mapper接口
├── mapping                                -- mybatis对应的mapping xml文件
├── jasper                                     -- 报表生成
├── pojo                                        -- 使用的pojo类
|          ├── dto                               -- 数据传输对象
├── service                                     -- service层接口
|          ├── base                              -- service基础接口
|          ├── impl                              -- service具体实现
├── shiro                                        -- shiro权限相关
|          ├── realm                            -- 自定义realm
|          ├── exception                      -- shiro异常
|          ├── filter                              -- 验证码实现
|          ├── entity                             -- session等
|          ├── service                            -- 有关shiro的service接口
|          ├── dao                                 
├── exception                                   -- 自定义异常
├── utils                                            -- 工具类
```

### 技术选型

#### 后端技术:
技术 | 名称 | 官网
----|------|----
Spring Framework | 容器  | [http://projects.spring.io/spring-framework/](http://projects.spring.io/spring-framework/)
SpringMVC | MVC框架  | [http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc)
Apache Shiro | 安全框架  | [http://shiro.apache.org/](http://shiro.apache.org/)
MyBatis | ORM框架  | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
MyBatis Generator | 代码生成  | [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)
PageHelper | MyBatis物理分页插件  | [http://git.oschina.net/free/Mybatis_PageHelper](http://git.oschina.net/free/Mybatis_PageHelper)
Druid | 数据库连接池  | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
Ehcache | 进程内缓存框架  | [http://www.ehcache.org/](http://www.ehcache.org/)
Log4J | 日志组件  | [http://logging.apache.org/log4j/1.2/](http://logging.apache.org/log4j/1.2/)
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/)

#### 前端技术:
技术 | 名称 | 官网
----|------|----
jQuery | 函式库  | [http://jquery.com/](http://jquery.com/)
Bootstrap | 前端框架  | [http://getbootstrap.com/](http://getbootstrap.com/)
Font-awesome | 字体图标  | [http://fontawesome.io/](http://fontawesome.io/)
zTree | 树插件  | [http://www.treejs.cn/v3/](http://www.treejs.cn/v3/)
jQuery EasyUI | 基于jQuery的UI插件集合体  | [http://www.jeasyui.com](http://www.jeasyui.com)

## 环境搭建（QQ群内有“zheng环境搭建和系统部署文档.doc”）

#### 开发工具:
- MySql: 数据库
- Tomcat: 应用服务器
- Git: 版本管理
- MyEclipse: 开发IDE
- webStorm: 开发IDE
- Navicat for MySQL: 数据库客户端

#### 开发环境：
- Jdk7+
- Mysql5.5+

### 预览图
![idea](images_shows/login.png)
![login](project-bootstrap/zheng-login.png)
![upms](project-bootstrap/zheng-upms.png)
![cms](project-bootstrap/zheng-cms.png)
![swagger](project-bootstrap/api.png)

### 项目总结

首先通过本项目，熟悉了对于SSM等框架的使用，熟悉一般的项目开发流程，但是项目仍然存在很多不合理的地方，比如命名不规范，目录设计不合理等等，这些问题都需要在实际项目中去学习，同时推荐大家看一看《阿里巴巴java开发手册》，将项目放在github,对自己目前学习阶段的一个总结，激励自己继续前进。如果你喜欢，欢迎fork本项目，并Pull Request您的commit。

### 不足之处

- 可以使用redis作为缓存，但未使用。
- 未实现单点登录。
- mysql优化问题，例如可以设置主从同步等等。
- 项目在单机运行，其实可以考虑分布式架构等等。
- 界面不够好看，未使用vue/react等流行框架。

期望在下一项目中，去深入分布式，去学习，在通往架构的路上越走越远。
学如逆水行舟，不进则退。

### 看过并设计到的优秀文章和博客（如有侵权，非本人本意，请通知删除，谢谢）
个人看过或者未看完的优秀文章，在此做个记录。

- [阿里中间件团队博客](http://jm.taobao.org/categories/%E5%88%86%E5%B8%83%E5%BC%8F/)

- [单点登录原理与简单实现](http://shuzheng5201314.iteye.com/blog/2343910 "单点登录原理与简单实现")

- [美团点评团队技术博客](https://tech.meituan.com/archives)

- [深入理解java并发](https://blog.csdn.net/column/details/19116.html?&page=1)

- [深入理解mybatis原理](https://blog.csdn.net/luanlouis/article/category/2309433)

- [跟我学Shiro目录贴](http://jinnianshilongnian.iteye.com/blog/2018398 "跟我学Shiro目录贴")

- [跟我学SpringMVC目录汇总贴](http://jinnianshilongnian.iteye.com/blog/1752171 "跟我学SpringMVC目录汇总贴")

- [跟我学spring3 目录贴](http://jinnianshilongnian.iteye.com/blog/1482071 "跟我学spring3 目录贴")

- [Redis中文网](http://www.redis.net.cn/ "Redis中文网")

- [Redis哨兵-实现Redis高可用](http://redis.majunwei.com/topics/sentinel.html "Redis哨兵-实现Redis高可用")

- [spring随笔](https://www.cnblogs.com/xrq730/category/739345.html)

- [mybatis-genarator 自定义插件](https://my.oschina.net/alexgaoyh/blog/702791 "mybatis-genarator 自定义插件")

### 在线文档

- [JDK7英文文档](http://tool.oschina.net/apidocs/apidoc?api=jdk_7u4 "JDK7英文文档")

- [Spring4.x文档](http://spring.oschina.mopaas.com/ "Spring4.x文档")

- [Mybatis3官网](http://www.mybatis.org/mybatis-3/zh/index.html "Mybatis3官网")

- [Bootstrap在线手册](http://www.bootcss.com/ "Bootstrap在线手册")

- [Git官网中文文档](https://git-scm.com/book/zh/v2 "Git官网中文文档")

## 许可证

[MIT](LICENSE "MIT")
