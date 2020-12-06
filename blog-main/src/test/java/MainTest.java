import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.StartApplication;
import com.roderick.mapper.ArticleMapper;
import com.roderick.pojo.Article;
import com.roderick.service.ArticleService;
import com.roderick.service.UserService;
import com.roderick.util.PageUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SpringBootTest(classes = StartApplication.class)
public class MainTest {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleService articleService;
    @Autowired
    PageUtil pageUtil;
    @Autowired
    UserService userService;

    @Test
    public void pageTest() {
        Page<Article> page = articleService.getArticleListByPageAndUidOrderByTime(
                "d7885613210d4274bbb00834965b7155",
                1, 5);
    }

    @Test
    public void articleTest() {
        Article article = articleMapper.selectById(4);
        System.out.println(article.getFormattedCreateTime());
    }

    @Test
    public void util() {
        System.out.println(pageUtil.getTotalPage(3L, 5L));
    }

    @Test
    public void userTest() {
        System.out.println(userService.getPostsByUid("d7885613210d4274bbb00834965b7155"));
    }

    @Test
    public void asyncTest() throws InterruptedException {
        /*articleService.asyncIncreaseViews(2L);
        Thread.sleep(5000L);*/
        articleMapper.increaseViewsById(2L);
    }

    public static void main(String[] args) {
        Base64 base64 = new Base64();
        System.out.println(new String(base64.decode("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"), StandardCharsets.UTF_8));
    }
}