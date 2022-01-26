package com.roderick.util;

import org.springframework.stereotype.Component;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

/**
 * 校验文件是否为图片
 */
@Component
public class ImageCheckUtil {
    private final MimetypesFileTypeMap mimetypesFileTypeMap;

    public ImageCheckUtil() {
        mimetypesFileTypeMap = new MimetypesFileTypeMap();
        /* 不添加下面的类型会造成误判 详见：http://stackoverflow.com/questions/4855627/java-mimetypesfiletypemap-always-returning-application-octet-stream-on-android-e*/
        mimetypesFileTypeMap.addMimeTypes("image png tif jpg jpeg bmp");
    }

    public boolean isImage(File file) {
        String mimetype = mimetypesFileTypeMap.getContentType(file);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }
}
