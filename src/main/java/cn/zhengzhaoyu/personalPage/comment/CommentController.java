package cn.zhengzhaoyu.personalPage.comment;

import cn.zhengzhaoyu.personalPage.common.controller.BaseController;
import cn.zhengzhaoyu.personalPage.common.service.LogService;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;

public class CommentController extends BaseController {
    private static final CommentService cs = new CommentService();
    private static final LogService ls = new LogService();

    @Before({POST.class})
    public void addComment() {
        int type = getParaToInt();
        String name = getPara("commentName");
        String text = getPara("commentText");
        ls.addLog("Comment type "+type+" add.", getIp());
        renderJson();
    }
}
