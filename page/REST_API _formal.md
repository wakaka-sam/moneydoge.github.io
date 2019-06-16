## REST API 设计规范

我们以[微软标准](<https://docs.microsoft.com/zh-cn/azure/architecture/best-practices/api-design>)为基础

在这之上，我们规定了我们的一些其他规范来统一api。

- 1.使用JSON进行数据的传输。
- 2.用状态码来代表请求是否正常执行。
- 3.需要用前缀来区分不同api的作用范围。