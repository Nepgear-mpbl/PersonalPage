package cn.zhengzhaoyu.personalPage.common.kit;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import org.apache.commons.mail.SimpleEmail;

/**
 * 邮件发送工具类
 */
public class EmailKit {

    private static final Log log = Log.getLog(EmailKit.class);
    private static String emailServer;
    private static String fromEmail;
    private static String password;

    public static void init(String emailServer, String fromEmail, String password) {
        EmailKit.emailServer = emailServer;
        EmailKit.fromEmail = fromEmail;
        EmailKit.password = password;
    }

    public static String sendEmail(String toEmail, String title, String content) {
        return sendEmail(emailServer, fromEmail, password, toEmail, title, content);
    }

    private static String sendEmail(String emailServer, String fromEmail, String password, String toEmail, String title, String content) {

        SimpleEmail email = new SimpleEmail();
        email.setHostName(emailServer);

        // 如果密码为空，则不进行认证
        if (StrKit.notBlank(password)) {
            email.setAuthentication(fromEmail, password);
        }

        email.setCharset("utf-8");
        try {
            email.addTo(toEmail);
            email.setFrom(fromEmail);
            email.setSubject(title);
            email.setMsg(content);
            return email.send();
        } catch (org.apache.commons.mail.EmailException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

}