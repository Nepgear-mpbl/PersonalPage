package cn.zhengzhaoyu.personalPage.picture;

import cn.zhengzhaoyu.personalPage.comment.CommentService;
import cn.zhengzhaoyu.personalPage.common.controller.BaseController;
import cn.zhengzhaoyu.personalPage.common.service.LogService;
import cn.zhengzhaoyu.personalPage.common.service.TypeService;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Nepge on 2017/7/19.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class PictureController extends BaseController {
    private static final PictureService ps = new PictureService();
    private static final CommentService cs = new CommentService();
    private static final TypeService ts = new TypeService();
    private static final LogService ls = new LogService();

    @Before({GET.class})
    public void index() {
        int pageNum = 1;
        if (null != getParaToInt("page")) {
            pageNum = getParaToInt("page");
        }
        Page<Record> pictures;
        if (null == getParaToInt("type")) {
            pictures=ps.getAllPictures(6,pageNum);
        } else {
            int type=getParaToInt("type");
            setAttr("type", type);
            pictures=ps.getPicturesByType(type, 6, pageNum);
        }
        if (0 == pictures.getList().size() && 0 != pictures.getTotalRow()) {
            renderError(404);
        }
        List<Record> types= ts.getPictureType();
        setAttr("types", types);
        setAttr("pictures", pictures);
        render("index.html");
    }

    @Before({GET.class})
    public void picInfo() {
        int picId = getParaToInt("id");
        Record picture = ps.findPicturesByIdWithType(picId);
        if (null == picture) {
            renderError(404);
        }
        List<Record> commentList = cs.getCommentsByTypeAndParent(0, picId);
        setAttr("commentList", commentList);
        setAttr("picture", picture);
        render("picInfo.html");
    }

    @Before({POST.class})
    public void uploadPicture() throws IOException {
        int maxSize = 10 * 1024 * 1024;
        UploadFile uploadFile = getFile("file", "/pictures/", maxSize);
        String title = getPara("title");
        String introduction = getPara("introduction");
        int imgType = getParaToInt("type");;
        File file = uploadFile.getFile();
        String type = file.getName().substring(file.getName().lastIndexOf('.'));
        String uuid = StrKit.getRandomUUID();
        String AbsPath = file.getAbsolutePath();
        String path = AbsPath.substring(0, AbsPath.lastIndexOf('\\')) + '\\' + uuid + type;
        if (file.renameTo(new File(path))) {
            Thumbnails.of(path)
                    .size(650, 1000)
                    .toFile(AbsPath.substring(0, AbsPath.lastIndexOf('\\')) + "\\thumbnails\\" + uuid + type);
            ls.addLog("Picture add.", getIp());
            renderJson(ps.addPicture(uuid, type, title, introduction, imgType));
        } else {
            file.delete();
            renderJson(Ret.by("status", false).set("message", "文件存储失败"));
        }
    }

    @Before({POST.class})
    public void removePicture() {
        int picId=getParaToInt();
        renderJson(ps.deletePicture(picId));
    }
}
