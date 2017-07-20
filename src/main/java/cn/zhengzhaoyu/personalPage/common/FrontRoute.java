package cn.zhengzhaoyu.personalPage.common;

import cn.zhengzhaoyu.personalPage.comment.CommentController;
import cn.zhengzhaoyu.personalPage.common.controller.GuestController;
import cn.zhengzhaoyu.personalPage.common.controller.ImgController;
import cn.zhengzhaoyu.personalPage.index.IndexController;
import cn.zhengzhaoyu.personalPage.picture.PictureController;
import com.jfinal.config.Routes;

/**
 * Created by Nepge on 2017/7/18.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class FrontRoute extends Routes {
    @Override
    public void config() {
        setBaseViewPath("_view");
        add("/", IndexController.class, "/index");
        add("img", ImgController.class);
        add("guest", GuestController.class);
        add("picture", PictureController.class);
        add("comment", CommentController.class);
    }
}
