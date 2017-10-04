package cn.zhengzhaoyu.personalPage.email;

import cn.zhengzhaoyu.personalPage.common.kit.EmailKit;
import cn.zhengzhaoyu.personalPage.common.model.Email;
import com.jfinal.kit.Ret;

import java.util.List;

public class EmailService {
    public static final EmailService me = new EmailService();
    private static final Email emailDao = new Email().dao();

    public Ret addEmail(String address) {
        if (null != emailDao.findFirst(emailDao.getSqlPara("email.findByAddress",address))){
            return Ret.by("status", false).set("message", "请不要重复订阅");
        }
        Email email = new Email();
        email.setAddress(address);
        if (email.save()) {
            return Ret.by("status", true).set("message", "订阅成功");
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }

    public List<Email> getEmails() {
        return emailDao.find(emailDao.getSqlPara("email.getAll"));
    }

    public Ret deleteEmail(int emailId) {
        Email email = emailDao.findFirst(emailDao.getSqlPara("email.findById", emailId));
        if (null == email) {
            return Ret.by("status", false).set("message", "邮箱不存在");
        }
        if (email.delete()) {
            return Ret.by("status", true).set("message", "删除成功");
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }

    public void sendEmails(String title){
        List<Email> emailList=emailDao.find(emailDao.getSqlPara("email.getAll"));
        if(null == emailList){
            return;
        }
        EmailKit.init("smtp.mxhichina.com","no-reply@zhengzhaoyu.cn","ZZy19980907");
        for (Email e:emailList
             ) {
            EmailKit.sendEmail(e.getAddress(),"RainVerse主页随笔更新提醒",
                    "感谢您订阅RainVerse的随笔\nRainVere刚刚上传了随笔《"+title+"》，您可以前往www.zhengzhaoyu.cn 查看最新动态。");
        }
    }
}
