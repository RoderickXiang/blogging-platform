package com.roderick.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * MinIO的文件交互
 */
public interface FileServiceNew {

    /**
     * 上传图片文件
     * @param file 前端的图片文件
     * @param bucketName 数据集的名字
     * @return true or false
     */
    boolean uploadImage(MultipartFile file, String bucketName);

    /**
     * 获取文件
     * @param bucketName 数据集的名字
     * @param fileName 文件名
     * @param response 用于存储输出流返回给调用方
     * @return 从文件服务器返回的数据
     */
    InputStream downloadFile(String bucketName, String fileName, HttpServletResponse response);
}
