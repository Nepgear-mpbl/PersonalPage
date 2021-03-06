package cn.zhengzhaoyu.personalPage.picture;

import cn.zhengzhaoyu.personalPage.comment.CommentService;
import cn.zhengzhaoyu.personalPage.common.model.Comment;
import cn.zhengzhaoyu.personalPage.common.model.Picture;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.io.File;
import java.util.List;

/**
 * Created by Nepge on 2017/7/20.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class PictureService {
    public static final PictureService me = new PictureService();
    public static final CommentService cs = new CommentService();
    private static final Picture pictureDao = new Picture().dao();

    public Ret addPicture(String uuid, String type, String title, String introduction, int imgType) {
        Picture picture = new Picture();
        String path = "pictures/" + uuid + type;
        String thumbnailPath = "pictures/thumbnails/" + uuid + type;
        picture.setIntroduction(introduction).setUuid(uuid).setPath(path).setTitle(title).setType(imgType).setThumbnailPath(thumbnailPath);
        if (picture.save()) {
            return Ret.by("status", true).set("message", "上传成功");
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }

    public Page<Record> getPicturesByType(int type, int pageSize, int pageNumber) {
        return Db.paginate(pageNumber, pageSize, pictureDao.getSqlPara("picture.getByType", type));
    }

    public Page<Record> getAllPictures(int pageSize,int pageNumber) {
        return Db.paginate(pageNumber, pageSize,pictureDao.getSqlPara("picture.getAll"));
    }

    public Record findPicturesByIdWithType(int picId) {
        return Db.findFirst(pictureDao.getSqlPara("picture.findByIdWithType", picId));
    }

    public Ret deletePicture(int picId) {
        Picture picture = pictureDao.findFirst(pictureDao.getSqlPara("picture.findById", picId));
        if (null == picture) {
            return Ret.by("status", false).set("message", "图片不存在");
        }
        if (picture.delete()) {
            File picFile = new File(PropKit.get("baseDownloadPath") + '/' + picture.getPath());
            if (picFile.delete()) {
                File thumFile = new File(PropKit.get("baseDownloadPath") + '/' + picture.getThumbnailPath());
                if (thumFile.delete()) {
                    List<Comment> commentList = cs.getCommentsByTypeAndParent_entity(0, picId);
                    for (Comment c : commentList
                            ) {
                        c.delete();
                    }
                    return Ret.by("status", true).set("message", "删除成功");
                } else {
                    return Ret.by("status", false).set("message", "缩略图删除失败");
                }
            } else {
                return Ret.by("status", false).set("message", "文件删除失败");
            }
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }
}
