package com.roderick.util;

import com.roderick.controller.Test;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class FileUtil {

    @Value("${minio.endpoint}")
    String MINIO_ENDPOINT;

    @Value("${minio.username}")
    private String MINIO_USERNAME;

    @Value("${minio.password}")
    private String MINIO_PASSWORD;

    private MinioClient minioClient;

    private ImageCheckUtil imageCheckUtil;

    @Autowired
    public void setImageCheckUtil(ImageCheckUtil imageCheckUtil) {
        this.imageCheckUtil = imageCheckUtil;
    }

    /*
      初始化MinIO文件工具类
     */
    @PostConstruct
    private void initFileUtil() {
        minioClient = MinioClient.builder()
                .endpoint(MINIO_ENDPOINT)
                .credentials(MINIO_USERNAME, MINIO_PASSWORD)
                .build();
    }

    public boolean uploadImage(MultipartFile file, String bucketName)
            throws IOException,
            ServerException,
            InsufficientDataException,
            ErrorResponseException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException {

        // 校验文件是否为图片
        if (!imageCheckUtil.isImage(MultipartFileToFile.multipartFileToFile(file))) {
            System.out.println("非图片文件");
            return false;
        }

        // Upload unknown sized input stream.
        InputStream inputStream = new ByteArrayInputStream(file.getBytes());
        String fileName = file.getName();
        String suffix = "".equals(fileName) ? ".jpg" : fileName.substring(fileName.lastIndexOf("."));
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName)
                        .object(UUIDUtil.getUUID() + suffix)
                        .stream(inputStream, -1, 10485760)
                        .contentType(file.getContentType())
                        .build());
        return true;
    }

    public void test() {
        System.out.println(MINIO_ENDPOINT);
    }
}
