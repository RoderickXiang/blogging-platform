package com.roderick.service.impl;

import com.roderick.bo.Base64ToMultipartFile;
import com.roderick.service.FileServiceNew;
import com.roderick.util.*;
import io.minio.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class FileServiceNewImpl implements FileServiceNew {

    @Value("${minio.endpoint}")
    String MINIO_ENDPOINT;

    @Value("${minio.username}")
    private String MINIO_USERNAME;

    @Value("${minio.password}")
    private String MINIO_PASSWORD;

    @Value("${minio.bucket}")
    private String MINIO_BUCKET;

    private MinioClient minioClient;

    /*
      初始化MinIO文件服务
     */
    @PostConstruct
    private void initFileUtil() {
        minioClient = MinioClient.builder()
                .endpoint(MINIO_ENDPOINT)
                .credentials(MINIO_USERNAME, MINIO_PASSWORD)
                .build();

        // 在服务器中检验bucket是否存在
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(MINIO_BUCKET)
                    .build());

            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(MINIO_BUCKET)
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadImage(MultipartFile file, String folderName) {
        try {
            // 校验文件是否为图片
            BufferedImage read = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            if (read == null) {
                System.out.println("非图片文件");
                return "";
            }
            // Upload unknown sized input stream.
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            String fileName = file.getOriginalFilename();
            String suffix;
            if ("".equals(fileName) || fileName == null) {
                suffix = ".jpg";
            } else {
                suffix = fileName.substring(fileName.lastIndexOf("."));
            }
            String fileNameInServer = UUIDUtil.getUUID() + suffix;
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(MINIO_BUCKET)
                            .object(folderName + '/' + fileNameInServer)
                            .stream(inputStream, -1, 10485760)
                            .contentType(file.getContentType())
                            .build());

            inputStream.close();
            return fileNameInServer;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String uploadImage(String imageBase64, String folderName) {
        String base64 = imageBase64.split(",")[1];
        String dataUri = imageBase64.split(",")[0];
        // 使用自定义MultipartFile
        return uploadImage(new Base64ToMultipartFile(base64, dataUri), folderName);
    }

    @Override
    public InputStream downloadFile(String folderName, String fileName, HttpServletResponse response) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(MINIO_BUCKET)
                            .object(folderName + "/" + fileName)
                            .build());

            ServletOutputStream outputStream = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = stream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            stream.close();
            outputStream.close();
            return stream;
        } catch (Exception e) {
            System.out.println("请求文件不存在");
            e.printStackTrace();
            return null;
        }
    }
}
