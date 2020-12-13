package com.roderick.api;

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

    /**
     * 上传头像
     *
     * @param image base64文件
     */
    @PostMapping("/avatar")
    public void uploadAvatar(String image) {
        System.out.println(image);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("editormd-image-file") MultipartFile file) {
        System.out.println(file.getName());
        System.out.println(file.isEmpty());
        System.out.println(file.getSize());
        Map<String, Object> map = new HashMap<>();
        map.put("success", 1);
        map.put("message", "上传成功");
        map.put("url", "http://localhost:8081/image/v2-13cc8d463e1ac9cd2bfbd7fbd62e05f2_hd.jpg");
        return ResponseEntity.ok(map);
    }
}
