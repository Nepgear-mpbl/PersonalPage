package cn.zhengzhaoyu.personalPage.picture;

import cn.zhengzhaoyu.personalPage.common.model.Picture;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Nepge on 2017/7/20.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class PictureService {
    public static final PictureService me = new PictureService();
    private static final Picture pictureDao = new Picture().dao();

    public Ret addPicture(String uuid, String type, String title, String introduction, int imgType) {
        Picture picture = new Picture();
        String path = "pictures/" + uuid + type;
        picture.setIntroduction(introduction).setUuid(uuid).setPath(path).setTitle(title).setType(imgType);
        if (picture.save()) {
            return Ret.by("status", true).set("message", "上传成功");
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }

    public Page<Record> getPicturesByType(int type, int pageSize, int pageNumber) {
        return Db.paginate(pageNumber, pageSize, pictureDao.getSqlPara("picture.getByType", type));
    }
    public Record findPicturesById(int picId) {
        return Db.findFirst(pictureDao.getSqlPara("picture.findById", picId));
    }
}
