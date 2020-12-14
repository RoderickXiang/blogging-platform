package com.roderick.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传文件到指定文件夹
     *
     * @param file 文件
     * @return 文件服务器中文件名
     */
    String uploadImageToFolder(MultipartFile file);
}
