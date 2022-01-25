import com.roderick.StartApplication;
import com.roderick.util.FileUtil;
import io.minio.errors.*;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest(classes = StartApplication.class)
public class MinIOTest {

    @Autowired
    FileUtil fileUtil;

    @Test
    public void uploadTest() throws IOException {
        File file = new File("C:\\Users\\30979\\Pictures\\头像\\新建文本文档.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        try {
            System.out.println(fileUtil.uploadImage(multipartFile, "asiatrip"));
        } catch (IOException | ServerException | InsufficientDataException | ErrorResponseException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            e.printStackTrace();
        }
    }


}
