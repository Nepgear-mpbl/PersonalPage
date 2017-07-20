package cn.zhengzhaoyu.personalPage.index;

import cn.zhengzhaoyu.personalPage.common.controller.BaseController;

/**
 * 主页controller
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class IndexController extends BaseController{
    public void index(){
        render("index.html");
    }
}
