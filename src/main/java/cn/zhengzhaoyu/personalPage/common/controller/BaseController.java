package cn.zhengzhaoyu.personalPage.common.controller;

import cn.zhengzhaoyu.personalPage.common.kit.IpKit;
import com.jfinal.core.Controller;

/**
 * Created by Nepge on 2017/7/18.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class BaseController extends Controller{
    public String getIp() {
        return IpKit.getRealIp(getRequest());
    }
}
