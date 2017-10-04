package cn.zhengzhaoyu.personalPage.email;

import cn.zhengzhaoyu.personalPage.common.controller.BaseController;
import cn.zhengzhaoyu.personalPage.common.model.Email;
import cn.zhengzhaoyu.personalPage.common.service.LogService;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;

import java.util.List;

public class EmailController extends BaseController{
    private static final EmailService es = new EmailService();
    private static final LogService ls = new LogService();

    @Before({GET.class})
    public void index() {
        List<Email> emailList= es.getEmails();
        setAttr("emailList",emailList);
        render("index.html");
    }

    @Before({POST.class,EmailValidator.class})
    public void uploadEmail() {
        String address = getPara("address");
        ls.addLog("Email add.", getIp());
        renderJson(es.addEmail(address));
    }

    @Before({POST.class})
    public void removeEmail() {
        int emailId = getParaToInt();
        renderJson(es.deleteEmail(emailId));
    }
}
