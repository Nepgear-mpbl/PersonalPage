package cn.zhengzhaoyu.personalPage.common.service;

import cn.zhengzhaoyu.personalPage.common.model.Log;

/**
 * Created by Nepge on 2017/7/20.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class LogService {
    public static final LogService me = new LogService();
    private static final Log logDao = new Log().dao();
    public void addLog(String oprateType,String ip){
        Log log=new Log();
        log.setType(oprateType).setOprateIp(ip).save();
    }
}
