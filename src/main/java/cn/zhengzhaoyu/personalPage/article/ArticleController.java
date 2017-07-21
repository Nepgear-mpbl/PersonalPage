package cn.zhengzhaoyu.personalPage.article;

import cn.zhengzhaoyu.personalPage.comment.CommentService;
import cn.zhengzhaoyu.personalPage.common.controller.BaseController;
import cn.zhengzhaoyu.personalPage.common.service.LogService;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * Created by Nepge on 2017/7/19.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class ArticleController extends BaseController {
    private static final ArticleService as = new ArticleService();
    private static final CommentService cs = new CommentService();
    private static final LogService ls = new LogService();

    @Before({GET.class})
    public void index() {
        int pageNum = 1;
        if (null != getParaToInt()) {
            pageNum = getParaToInt();
        }
        Page<Record> article_0 = as.getArticlesByType(0, 10, pageNum);
        if (0 == article_0.getList().size()) {
            renderError(404);
        }
        setAttr("article_0", article_0);
        render("index.html");
    }

    @Before({GET.class})
    public void articleInfo() {
        int articleId = getParaToInt("id");
        Record article = as.findArticlesById(articleId);
        if (null == article) {
            renderError(404);
        }
        List<Record> commentList = cs.getCommentsByTypeAndParent(0, articleId);
        setAttr("commentList", commentList);
        setAttr("article", article);
        render("articleInfo.html");
    }

    @Before({POST.class})
    public void uploadArticles() {
        String title = getPara("title");
        String text = getPara("text");
        String _abstract=getPara("abstract");
        int articleType = 0;
        ls.addLog("Article type " + articleType + " add.", getIp());
        renderJson(as.addArticle(articleType,title,text,_abstract));
    }
}
