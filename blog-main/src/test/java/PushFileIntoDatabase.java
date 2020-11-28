import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roderick.StartApplication;
import com.roderick.mapper.ArticleMapper;
import com.roderick.pojo.Article;
import com.roderick.pojo.ArticleCategory;
import com.roderick.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.print.Book;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = StartApplication.class)
public class PushFileIntoDatabase {

    @Autowired
    ArticleMapper articleMapper;

    int count = 0;

    @Test
    public void start() {
        File file = new File("F:\\2020-11-28");
        this.pushFile(file);
    }

    public void pushFile(File file) {
        if (!file.isDirectory()) {  //判断是否为文件夹
            String name = file.getName();
            //推入数据库
            if (name.endsWith(".md")) {
                Article article = new Article();
                article.setTitle(name.split("\\.")[0]);
                article.setContent(this.getContent(file));
                /*浏览量默认为0*/
                article.setAuthorUid("d7885613210d4274bbb00834965b7155");
                article.setAuthorName("RoderickXiang");

                //articleMapper.insert(article);
            }
        } else {
            File[] directories = file.listFiles();
            if (directories == null) {
                return;
            }
            for (File directory : directories) {
                this.pushFile(directory);
            }
        }
    }

    /**
     * 获取文件内容
     */
    public String getContent(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            char[] buffer = new char[2048];
            StringBuilder result = new StringBuilder();
            int len;
            while ((len = bufferedReader.read(buffer, 0, 500)) != -1) {
                result.append(buffer, 0, len);
            }
            return result.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
