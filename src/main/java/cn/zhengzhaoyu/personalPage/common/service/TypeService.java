package cn.zhengzhaoyu.personalPage.common.service;

import cn.zhengzhaoyu.personalPage.common.model.ArticleType;
import cn.zhengzhaoyu.personalPage.common.model.PictureType;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class TypeService {
    public static final TypeService me = new TypeService();
    private static final ArticleType articleTypeDao = new ArticleType().dao();
    private static final PictureType pictureTypeDao = new PictureType().dao();
    public List<Record> getArticleType() {
        return Db.find(articleTypeDao.getSqlPara("type.getArticleType"));
    }
    public List<Record> getPictureType() {
        return Db.find(articleTypeDao.getSqlPara("type.getPictureType"));
    }
}
