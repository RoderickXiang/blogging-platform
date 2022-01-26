package com.roderick.api;

import com.roderick.service.FileService;
import com.roderick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageApi {

    @Value("${file.server.image-article}")
    String IMAGE_ARTICLE_PREFIX; //文章中图片在服务器中地址的前缀
    @Value("${file.server.avatar}")
    String AVATAR_PREFIX;   //头像在服务器中地址的前缀

    FileService fileService;
    UserService userService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 上传头像
     *
     * @param image base64文件
     */
    /*@PostMapping("/upload/avatar")*/
    public ResponseEntity<Map<String, Object>> uploadAvatar(String image, String uid) {
        String strBase64 = image.substring(image.lastIndexOf(',') + 1);
        String fileName = fileService.uploadImageToFolder(strBase64);

        //修改数据库中头像信息
        userService.updateUserAvatar(uid, AVATAR_PREFIX + '/' + fileName);

        //返回消息
        Map<String, Object> result = new HashMap<>();
        if (!"".equals(fileName)) {
            result.put("success", 1);
            result.put("message", "上传成功");
            result.put("url", AVATAR_PREFIX + '/' + fileName);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    /**
     * markdown图床存储图片
     *
     * @param file 图片
     */
    /*@PostMapping("/upload/image")*/
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("editormd-image-file") MultipartFile file) {
        //上传文件到文件服务器文件夹
        String fileName = fileService.uploadImageToFolder(file);

        //返回json结果
        Map<String, Object> result = new HashMap<>();
        if (fileName != null) {
            result.put("success", 1);
            result.put("message", "上传成功");
            result.put("url", IMAGE_ARTICLE_PREFIX + '/' + fileName);
        } else {
            result.put("success", 0);
            result.put("message", "上传失败");
        }
        return ResponseEntity.ok(result);
    }
}
