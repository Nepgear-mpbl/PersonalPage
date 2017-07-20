package cn.zhengzhaoyu.personalPage.comment;

import cn.zhengzhaoyu.personalPage.common.model.Comment;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * Created by Nepge on 2017/7/20.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class CommentService {
    public static final CommentService me = new CommentService();
    private static final Comment commentDao = new Comment().dao();

    public Ret addComment(String commentName, String commentText, int type,int parentId) {
        Comment comment = new Comment();
        comment.setCommentName(commentName).setCommentText(commentText).setType(type).setParent(parentId);
        if (comment.save()) {
            return Ret.by("status", true).set("message", "上传成功");
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }

    public List<Record> getCommentsByTypeAndParent(int type,int parentId) {
        return Db.find(commentDao.getSqlPara("comment.getByTypeAndParent", type,parentId));
    }
}
