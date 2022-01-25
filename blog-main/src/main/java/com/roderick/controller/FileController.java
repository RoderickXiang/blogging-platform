package com.roderick.controller;

import com.roderick.service.FileServiceNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
public class FileController {

    FileServiceNew fileServiceNew;

    @Autowired
    public void setFileServiceNew(FileServiceNew fileServiceNew) {
        this.fileServiceNew = fileServiceNew;
    }

    @GetMapping("/images/{bucketName}/{imageName}")
    public ResponseEntity<Object> downloadImage(@PathVariable String bucketName, @PathVariable String imageName, HttpServletResponse response) {
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(imageName);
        // 设置文件类型
        response.setContentType(mediaType.orElse(MediaType.TEXT_HTML).toString());
        if (fileServiceNew.downloadFile(bucketName, imageName, response) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
