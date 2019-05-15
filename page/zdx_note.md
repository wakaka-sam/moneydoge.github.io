## 学习笔记



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



