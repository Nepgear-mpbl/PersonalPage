package cn.zhengzhaoyu.personalPage.comment;

import cn.zhengzhaoyu.personalPage.common.controller.BaseController;
import cn.zhengzhaoyu.personalPage.common.service.LogService;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;

public class CommentController extends BaseController {
    private static final CommentService cs = new CommentService();
    private static final LogService ls = new LogService();

    @Before({POST.class,CommentValidator.class})
    public void addComment() {
        int type = getParaToInt(0);
        int parentId = getParaToInt(1);
        String name = getPara("name");
        String text = getPara("text");
        ls.addLog("Comment type " + type + " add.", getIp());
        renderJson(cs.addComment(name, text, type, parentId));
    }

    @Before({POST.class})
    public void removeComment() {
        int commentId = getParaToInt();
        renderJson(cs.deleteComment(commentId));
    }
}
