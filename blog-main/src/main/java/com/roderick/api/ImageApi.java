package com.roderick.api;

import com.roderick.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageApi {

    @Value("${file.server.image-article}")
    String IMAGE_ARTICLE_PREFIX; //文章中图片在服务器中地址的前缀

    FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 上传头像
     *
     * @param image base64文件
     */
    @PostMapping("/avatar")
    public void uploadAvatar(String image) {
        System.out.println(image);
    }


    /**
     * markdown图床存储图片
     *
     * @param file 图片
     */
    @PostMapping("/upload/image")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("editormd-image-file") MultipartFile file) {
        //上传文件到文件服务器文件夹
        String fileName = fileService.uploadImageToFolder(file);

        //返回json结果
        Map<String, Object> map = new HashMap<>();
        if (fileName != null) {
            map.put("success", 1);
            map.put("message", "上传成功");
            map.put("url", IMAGE_ARTICLE_PREFIX + '/' + fileName);
        } else {
            map.put("success", 0);
            map.put("message", "上传失败");
        }
        return ResponseEntity.ok(map);
    }
}
