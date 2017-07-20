package cn.zhengzhaoyu.personalPage.common.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;

/**
 * Created by Nepge on 2017/7/19.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class ImgController extends BaseController {
    @Before({GET.class})
    public void index() {
        String fileName = getPara("fileName");
        renderFile(fileName);
    }
}
