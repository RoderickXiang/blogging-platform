import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.StartApplication;
import com.roderick.pojo.Article;
import com.roderick.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(classes = StartApplication.class)
public class MainTest {

    @Autowired
    ArticleService articleService;

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    @Test
    public void pageTest() {
        Page<Article> page = articleService.getArticleListByPageOrderByTime(1, 1);
        System.out.println(page);
        System.out.println(page.getRecords());
    }
}
