package cn.zhengzhaoyu.personalPage.picture;

import cn.zhengzhaoyu.personalPage.common.controller.BaseController;
import cn.zhengzhaoyu.personalPage.common.service.LogService;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import java.io.File;
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
    LogService ls=new LogService();

    @Before({GET.class})
    public void index() {
        int pageNum=1;
        if(null !=  getParaToInt()){
            pageNum=getParaToInt();
        }
        Page<Record> picture_0 = ps.getPicturesByType(0,6,pageNum);
        if(0 == picture_0.getList().size()) {
            renderError(404);
        }
        setAttr("picture_0", picture_0);
        render("index.html");
    }

    @Before({POST.class})
    public void uploadPicture() {
        int maxSize = 10 * 1024 * 1024;
        UploadFile uploadFile = getFile("file", "/pictures/", maxSize);
        String title = getPara("title");
        String introduction = getPara("introduction");
        int imgType = 0;
        File file = uploadFile.getFile();
        String type = file.getName().substring(file.getName().lastIndexOf('.'));
        String uuid = StrKit.getRandomUUID();
        String AbsPath = file.getAbsolutePath();
        String path = AbsPath.substring(0, AbsPath.lastIndexOf('\\')) + '\\' + uuid + type;
        if (file.renameTo(new File(path))) {
            ls.addLog("Picture add.",getIp());
            renderJson(ps.addPicture(uuid, type, title, introduction, imgType));
        } else {
            file.delete();
            renderJson(Ret.by("status", false).set("message", "文件存储失败"));
        }
    }
}
