## 简介
采用Spring Boot + Thymeleaf的博客社区项目，使用Nginx作为文件服务器，适用于课程设计  

## 技术栈

技术 | 解决方案
---|---
后端 | Spring Boot
前端模板引擎 | Thymeleaf
数据库 | MySQL 8.0 + MyBatis-Plus
文件服务器 | Nginx
监控 | Spring Boot Actuator + Spring Boot Admin

## 架构图
[![rjLX1P.png](https://s3.ax1x.com/2020/12/31/rjLX1P.png)](https://imgchr.com/i/rjLX1P)

## 简单展示
[![rjOB9I.png](https://s3.ax1x.com/2020/12/31/rjOB9I.png)](https://imgchr.com/i/rjOB9I)
[![rjXSv6.png](https://s3.ax1x.com/2020/12/31/rjXSv6.png)](https://imgchr.com/i/rjXSv6)
[![rjXMqS.png](https://s3.ax1x.com/2020/12/31/rjXMqS.png)](https://imgchr.com/i/rjXMqS)

## 部署
### Nginx配置
由于使用到了Nginx作为文件服务器的功能并且文件上传的实现是通过controller直接存入文件夹，所以在不同环境需要配置不同的文件夹路径  

Nginx服务器的目录结构：
```
nginx根目录/data/
└── blogging-platform   //项目名称
    ├── avatar          //头像
    └── image-article   //文章中的图片
```
配置好之后可以通过http方式：域名/avatar/图片名.jpg 获取图片  
config文件：
```
http {
    include       mime.types;
    default_type  application/octet-stream;

    sendfile        on;
    keepalive_timeout  65;

    server {
        listen       80;
        # server_name  localhost;

        # 图标
        location /favicon.ico {  
            root html;  
        }

        # 图床
		location /image-article {
            root data/blogging-platform;
		}

        # 头像
        location /avatar {
            root data/blogging-platform;
        }

        location / {
            proxy_pass  http://127.0.0.1:8080;
        }
        location = /50x.html {
            root   html;
        }
    }
}
```

## 下一步
1. 密码修改
2. 评论区
3. 问答模块
4. 类似LeetCode的Playground

## 使用到的个人开源项目

技术 | 解决方案
---|---
头像裁剪 + 上传 | https://github.com/saintic/layui-cropper-avatar