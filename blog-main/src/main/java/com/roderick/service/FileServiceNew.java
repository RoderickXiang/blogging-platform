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
     *
     * @param file       前端的图片文件
     * @param folderName 数据集中文件夹的名字
     * @return 在存储服务器中的文件名，空则为上传失败
     */
    String uploadImage(MultipartFile file, String folderName);

    String uploadImage(String strBase64, String folderName);

    /**
     * 获取文件
     *
     * @param folderName 数据集中文件夹的名字
     * @param fileName   文件名
     * @param response   用于存储输出流返回给调用方
     * @return 从文件服务器返回的数据
     */
    InputStream downloadFile(String folderName, String fileName, HttpServletResponse response);
}
