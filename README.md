# 项目介绍
    没有介绍就是最好的介绍
> 代码写的low
## 作者
Myxiaowang

> 拉下来配置自己的Yml环境

## 帮助


| 使用         | 版本       | 说明   |其他|
|:-----------|:---------|------|-----|
| Springboot | 2.7      | 无    |无|
| docker     | 20.10.11 | 部署IO |
| Minio      | latest   | 无    |docker run -p 9000:9000 -p 9001:9001 -d --name minio -v /opt/docker/minio/data:/data -v /opt/docker/minio/config:/root/.minio -e "MINIO_ROOT_USER=minio" -e "MINIO_ROOT_PASSWORD=minio@123456" minio/minio server /data --console-address ":9000" --address ":9001"||
> 加入了一个不是很好看的Swagger：
> 启动后访问你的地址+端口/swagger-ui.html



## 开发原因
学习Minio封装一套可用的工具类


#### Minio 接口文档地址
https://docs.min.io/docs/java-client-api-reference.html

#### 参考
https://juejin.cn/post/7101581935486615559   



> 注意 本项目不推荐使用controller层的接口 因为不稳
