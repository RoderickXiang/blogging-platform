import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.StartApplication;
import com.roderick.mapper.ArticleMapper;
import com.roderick.mapper.UserRoleMapper;
import com.roderick.pojo.Article;
import com.roderick.pojo.UserRole;
import com.roderick.service.ArticleService;
import com.roderick.service.UserService;
import com.roderick.util.Base64Util;
import com.roderick.util.PageUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

@SpringBootTest(classes = StartApplication.class)
public class MainTest {

    @Value("${avatar.folder}")
    String AVATAR_FOLDER;

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleService articleService;
    @Autowired
    PageUtil pageUtil;
    @Autowired
    UserService userService;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    Base64Util base64Util;

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
        System.out.println(passwordEncoder.matches("123", "$2a$10$p3njf.B8jY3DxciAmKJoVuUSfxWBo9n5tA8idgthzGbxBTybKtTxG"));
    }

    @Test
    public void asyncTest() throws InterruptedException {
        /*articleService.asyncIncreaseViews(2L);
        Thread.sleep(5000L);*/
        articleMapper.increaseViewsById(2L);
    }

    @Test
    public void base64Test() {
        String str = base64Util.file2Base64(new File("F:\\表情包\\640.png"));
        File file = new File(AVATAR_FOLDER + "/" + "test.jpg");
        base64Util.base64ToFile(str, file);
    }

}