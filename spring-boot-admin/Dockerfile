FROM java:8

COPY target/blog-spring-boot-admin-1.0.0-SNAPSHOT.jar spring-boot-admin.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","spring-boot-admin.jar"]