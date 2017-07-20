package cn.zhengzhaoyu.personalPage.common.controller;

import cn.zhengzhaoyu.personalPage.common.service.LogService;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.redis.Redis;

import java.util.HashMap;

/**
 * Created by Nepge on 2017/7/19.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class GuestController extends BaseController{
    private static final LogService ls=new LogService();
    @Before({POST.class})
    public void index() {
        String guestName=getPara();
        String token = StrKit.getRandomUUID();
        HashMap<String,Object> guest=new HashMap<>();
        guest.put("name",guestName);
        guest.put("status",true);
        Redis.use().set(token, guest);
        setCookie("Guest", token, 60 * 60);
        ls.addLog("Guest "+guestName+" visit.",getIp());
        renderJson(Ret.by("status", true).set("message","欢迎，"+guestName));
    }
}
