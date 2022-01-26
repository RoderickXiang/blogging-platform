import com.roderick.StartApplication;
import com.roderick.service.FileServiceNew;
import io.minio.errors.*;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest(classes = StartApplication.class)
public class MinIOTest {

    @Autowired
    FileServiceNew fileServiceNew;

    @Test
    public void imageUploadTest() throws Exception {
        File file = new File("C:\\Users\\30979\\Pictures\\头像\\新建文本文档.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        System.out.println(fileServiceNew.uploadImage(multipartFile, "test"));
    }

    @Test
    public void base64Test(){

    }
}
