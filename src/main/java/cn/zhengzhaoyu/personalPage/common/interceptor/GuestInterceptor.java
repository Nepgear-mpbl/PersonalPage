package cn.zhengzhaoyu.personalPage.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Redis;

import java.util.HashMap;

/**
 * Created by Nepge on 2017/7/19.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class GuestInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller c = inv.getController();
        String token = c.getCookie("Guest");
        HashMap guest = token == null ? null : Redis.use().get(token);
        if (guest != null) {
            c.setAttr("guest", guest);
        }
        inv.invoke();
    }
}
