FROM java:8

RUN mkdir -p /usr/local/nginx/data/blogging-platform && \
    mkdir /usr/local/nginx/data/blogging-platform/image-article && \
    mkdir /usr/local/nginx/data/blogging-platform/avatar && \
    echo "已创建数据文件夹"

COPY target/blog-main-1.0.0-SNAPSHOT.jar main.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","main.jar"]