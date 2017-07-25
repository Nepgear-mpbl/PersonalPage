package cn.zhengzhaoyu.personalPage.article;

import cn.zhengzhaoyu.personalPage.comment.CommentService;
import cn.zhengzhaoyu.personalPage.common.controller.BaseController;
import cn.zhengzhaoyu.personalPage.common.service.LogService;
import cn.zhengzhaoyu.personalPage.common.service.TypeService;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Redis;

import java.util.HashMap;
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
    private static final TypeService ts = new TypeService();
    private static final LogService ls = new LogService();

    @Before({GET.class})
    public void index() {
        int pageNum = 1;
        if (null != getParaToInt("page")) {
            pageNum = getParaToInt("page");
        }
        Page<Record> articles;
        if (null == getParaToInt("type")) {
            articles=as.getAllArticles(4,pageNum);
        } else {
            int type=getParaToInt("type");
            setAttr("type", type);
            articles=as.getArticlesByType(type, 4, pageNum);
        }
        if (0 == articles.getList().size() && 0 != articles.getTotalRow()) {
            renderError(404);
        }
        List<Record> types= ts.getArticleType();
        setAttr("types", types);
        setAttr("articles", articles);
        render("index.html");
    }

    @Before({GET.class})
    public void articleInfo() {
        int articleId = getParaToInt("id");
        Record article = as.findArticlesByIdWithType(articleId);
        if (null == article) {
            renderError(404);
        }
        List<Record> commentList = cs.getCommentsByTypeAndParent(1, articleId);
        setAttr("commentList", commentList);
        setAttr("article", article);
        render("articleInfo.html");
    }

    @Before({GET.class})
    public void add() {
        String token = getCookie("Guest");
        HashMap guest = token == null ? null : Redis.use().get(token);
        if (null != guest && (boolean) guest.get("status")) {
            List<Record> types= ts.getArticleType();
            setAttr("types", types);
            render("addArticle.html");
        } else {
            renderError(404);
        }
    }

    @Before({POST.class})
    public void uploadArticle() {
        String title = getPara("title");
        String text = getPara("text");
        String _abstract = getPara("abstract");
        int articleType = getParaToInt("type");
        ls.addLog("Article type " + articleType + " add.", getIp());
        renderJson(as.addArticle(articleType, title, text, _abstract));
    }

    @Before({POST.class})
    public void removeArticle() {
        int articleId = getParaToInt();
        renderJson(as.deleteArticle(articleId));
    }
}
