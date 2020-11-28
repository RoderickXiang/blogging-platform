import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.StartApplication;
import com.roderick.mapper.ArticleMapper;
import com.roderick.pojo.Article;
import com.roderick.service.ArticleService;
import com.roderick.util.PageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest(classes = StartApplication.class)
public class MainTest {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleService articleService;
    @Autowired
    PageUtil pageUtil;

    public static void main(String[] args) {
        //System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
        char[] chars = {'好','哈'};
        System.out.println(chars.length);
    }

    @Test
    public void pageTest() {
        Page<Article> page = articleService.getArticleListByPageOrderByTime(1, 2);
        System.out.println(page);
        System.out.println(page.getRecords());
    }

    @Test
    public void articleTest() {
        Article article = articleMapper.selectById(4);
        System.out.println(article.getFrontendCreateTime());
    }

    @Test
    public void util() {
        System.out.println(pageUtil.getTotalPage(3L, 5L));
    }
}