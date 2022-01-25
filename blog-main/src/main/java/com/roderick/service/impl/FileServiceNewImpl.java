package com.roderick.service.impl;

import com.roderick.service.FileServiceNew;
import com.roderick.util.ImageCheckUtil;
import com.roderick.util.MultipartFileToFile;
import com.roderick.util.UUIDUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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

    private MinioClient minioClient;

    private ImageCheckUtil imageCheckUtil;

    @Autowired
    public void setImageCheckUtil(ImageCheckUtil imageCheckUtil) {
        this.imageCheckUtil = imageCheckUtil;
    }

    /*
      初始化MinIO文件服务
     */
    @PostConstruct
    private void initFileUtil() {
        minioClient = MinioClient.builder()
                .endpoint(MINIO_ENDPOINT)
                .credentials(MINIO_USERNAME, MINIO_PASSWORD)
                .build();
    }

    @Override
    public boolean uploadImage(MultipartFile file, String bucketName) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public InputStream downloadFile(String bucketName, String fileName, HttpServletResponse response) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
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
            return null;
        }
    }
}
