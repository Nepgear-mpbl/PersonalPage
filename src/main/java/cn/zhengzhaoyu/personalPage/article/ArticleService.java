package cn.zhengzhaoyu.personalPage.article;

import cn.zhengzhaoyu.personalPage.common.model.Article;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Nepge on 2017/7/21.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class ArticleService {
    public static final ArticleService me = new ArticleService();
    private static final Article articleDao = new Article().dao();

    public Ret addArticle(int type, String title, String text, String _abstract) {
        Article article = new Article();
        article.setArticleAbstract(_abstract).setArticleText(text).setTitle(title).setType(type);
        if (article.save()) {
            return Ret.by("status", true).set("message", "上传成功");
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }

    public Page<Record> getArticlesByType(int type, int pageSize, int pageNumber) {
        return Db.paginate(pageNumber, pageSize, articleDao.getSqlPara("article.getByType", type));
    }

    public Record findArticlesById(int articleId) {
        return Db.findFirst(articleDao.getSqlPara("article.findById", articleId));
    }
    public Ret deleteArticle(int articleId) {
        Article article = articleDao.findFirst(articleDao.getSqlPara("article.findById", articleId));
        if (null == article) {
            return Ret.by("status", false).set("message", "文章不存在");
        }
        if (article.delete()) {
            return Ret.by("status", true).set("message", "删除成功");
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }
}
