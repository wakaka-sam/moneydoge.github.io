## 学习笔记

{:.no_toc}

- 目录 {:toc}



#### 如何初始化第一个spring boot程序

2019.4.9

- 先下载IDEA进行开发

  - 相比于Eclipse，IDEA可以自己自动先生成spring boot的基本框架，然后我们在这再添加我们所需的功能即可
  - 首先要下载IDEA，然后进行学生认证以获取免费账号

- 接着我们打开IDEA，根据教程来进行操作

  - *https://baijiahao.baidu.com/s?id=1608301601154521261&wfr=spider&for=pc*

- 值得注意的是，在连接数据库时，我这边采用的mysql是8.0版本的，所以有几个地方要与教程不一样

  ```yaml
  datasource:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/tb_test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false
      
  ```

  - jdbc前面要加上cj
  - 在地址后要加上**?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false** 原因是8.0要求要有时区，以及一些证书要求，所以需要加上后缀。

- 大致上跟着教程就能做出一个比较简单的RESTful API了



#### 关于数据库查询方法

2019.5.13

- 由于在最新的数据库中，QueryForInt和QueryForLong的方法已经失效了，所以我们要使用QueryForObject来替代

- queryForObject(String sql, Object[] args, Class<T> requiredType)

- 其中，第一个参数使用sql语句，第二个为参数，第三个是返回变量的类型，例：

  - ```
    count = jdbcTemplate.queryForObject(sql,new Object[] {openid},int.class);; 
    
    ```

- 值得注意的是，如果需要返回值是List形式的，使用queryForList即可。





#### 服务器的申请以及相关简单配置

2019.5.24

- 我们的服务器选择搭建在阿里云上，首先我们先进行学生认证，这样的话我们申请服务器，每个月的费用只需要10元，比较划算。
- 接着我们会看到相关的账户root以及初始密码，我们使用putty进行连接
- ![1558839276398](..\imgsrc\wakaka_img\1558839276398.png)
- 我们先更新密码passwd
- ![1558841569423](..\imgsrc\wakaka_img\1558841569423.png)
- 接着安装宝塔界面，宝塔有利于我们对服务器进行管理，例如安装mysql以及相关的端口开放等操作。
- 在服务器输入 wget -O install.sh http://download.bt.cn/install/install-ubuntu_6.0.sh && sudo bash install.sh 
- 安装完毕后，记得记录下初始密码。
- 然后我们可以在自己服务器的ip地址下的8888端口访问宝塔界面
- ![1558844928003](..\imgsrc\wakaka_img\1558844928003.png)
- 然后输入初始的账号以及密码即可进入到宝塔界面对服务器进行操作



#### 域名的申请以及配置

2019.5.24

- 我们去阿里云中进行域名的购买（我这边是在手机端进行购买的，所以就不贴图了）

- 我们找自己想要的域名，然后购买即可，通常一个club域名在10多块钱左右一年，然后再购买云解析服务即可。

- 然后我们在阿里云的解析服务中，为我们的域名添加解析，解析到我们的服务器上。后面访问这个域名，就会自动转到我们的服务器地址上。

- ![1558848471082](..\imgsrc\wakaka_img\1558848471082.png)

- 配置完后，我们访问域名就会直接跳转到我们服务器的ip地址上了

- ![1558849528201](..\imgsrc\wakaka_img\1558849528201.png)

  





#### 关于SSL证书以及https认证的方法

2019.5.24

- 我们先去阿里云的SSL证书中申请我们需要的SSL证书

- 点击申请，等待审核之后，我们就会得到一个已签发的证书。

- 我们使用的spring boot对应的服务端是tomcat，所以我们在下载界面下载Tomcat的证书。

- ![1558849697680](..\imgsrc\wakaka_img\1558849697680.png)

- 然后，我们会下载到一个txt和pfx文件。

- 我们在application.yml文件中，添加对应的pfx文件名字以及密码

- ```yaml
  server:
    port: 3336
    ssl:
      key-store: 2129067.pfx
      key-store-password: ocLB8JNI
    servlet:
      context-path: /
  ```

- 然后，我们复制pfx到项目的根目录下，以及部署后的jar包的目录下。之后即可访问https的域名了。

  

#### 如何使用swagger简单配置出api手册

2019.5.26

- 根据教程，这个比较简单，首先先添加依赖，在pom文件中

- ```yaml
  		<dependency>
              <groupId>io.springfox</groupId>
              <artifactId>springfox-swagger2</artifactId>
              <version>2.9.2</version>
          </dependency>
          <dependency>
              <groupId>io.springfox</groupId>
              <artifactId>springfox-swagger-ui</artifactId>
              <version>2.9.2</version>
          </dependency>
  
  
  ```

- 然后再编写一个简单的controller

- ```java
  package com.example.demo;
  
  import ...
  @Configuration
  @EnableSwagger2
  public class SwaggerController{
  
      @Bean
      public Docket createRestApi() {
          return new Docket(DocumentationType.SWAGGER_2)
                  .apiInfo(apiInfo())
                  .select()
                  .apis(RequestHandlerSelectors.any())
                  .paths(Predicates.not(PathSelectors.regex("/error.*")))// 错误路径不监控
                  .paths(PathSelectors.any())
                  .build();
      }
  
      private ApiInfo apiInfo() {
          return new ApiInfoBuilder()
                  .title("Moneydog")
                  .description("zdx后端接口描述")
                  .contact(
                          new Contact("wakaka", "moneydog.club", "zhengdx7@mail2.sysu.edu.cn")
                  )
                  .version("1.0.0-SNAPSHOT")
                  .build();
      }
  }
  ```

- 然后我们就可以再localhost:3336/swagger-ui.html# 直接看到我们的api手册了。

- 然后我们只需要在每个接口上，写上对应的备注，方便前端人员理解，就完成了

  - 在类上增加`Api`注解
  - 在方法上增加`ApiOperation`注解
  - 在参数上增加`ApiParam`注解
  - 在模型字段上`ApiModelProperty`增加注解
