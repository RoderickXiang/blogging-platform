package com.roderick.service.impl;

import com.roderick.service.FileService;
import com.roderick.util.Base64Util;
import com.roderick.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Value("${image-article.folder}")
    String IMAGE_ARTICLE_FOLDER;
    @Value("${avatar.folder}")
    String AVATAR_FOLDER;

    Base64Util base64Util;

    @Autowired
    public void setBase64Util(Base64Util base64Util) {
        this.base64Util = base64Util;
    }

    @Override
    public String uploadImageToFolder(MultipartFile file) {
        String original = file.getOriginalFilename();
        if (original != null) {
            try {
                String extension = original.substring(original.lastIndexOf("."));
                String fileName = "image_" + UUIDUtil.getUUID() + extension;    //新文件名
                file.transferTo(new File(IMAGE_ARTICLE_FOLDER + '/' + fileName));
                return fileName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public String uploadImageToFolder(String strBase64) {
        String fileName = "avatar_" + UUIDUtil.getUUID() + ".jpg";
        boolean flag = base64Util.base64ToFile(strBase64, new File(AVATAR_FOLDER + '/' + fileName));
        if (flag) {
            return fileName;
        }
        return null;
    }
}
