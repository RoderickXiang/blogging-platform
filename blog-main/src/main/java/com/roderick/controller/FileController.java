package com.roderick.controller;

import com.roderick.service.FileServiceNew;
import com.roderick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class FileController {

    @Value("${avatar.folder}")
    String AVATAR_FOLDER;

    FileServiceNew fileServiceNew;
    UserService userService;

    @Autowired
    public void setFileServiceNew(FileServiceNew fileServiceNew) {
        this.fileServiceNew = fileServiceNew;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/images/{folderName}/{imageName}")
    public ResponseEntity<Object> downloadImage(@PathVariable String folderName, @PathVariable String imageName, HttpServletResponse response) {
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(imageName);
        // 设置文件类型
        response.setContentType(mediaType.orElse(MediaType.TEXT_HTML).toString());
        if (fileServiceNew.downloadFile(folderName, imageName, response) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 上传头像
     *
     * @param image base64文件
     */
    @PostMapping("/upload/avatar")
    public ResponseEntity<Map<String, Object>> uploadAvatar(String image, String uid) {
        String fileName = fileServiceNew.uploadImage(image, "avatar");
        // 修改数据库中图片地址
        userService.updateUserAvatar(uid, "/images/" + AVATAR_FOLDER + '/' + fileName);

        //返回消息
        Map<String, Object> result = new HashMap<>();
        if (!"".equals(fileName)) {
            result.put("success", 1);
            result.put("message", "上传成功");
            result.put("url", "/images/" + AVATAR_FOLDER + '/' + fileName);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
