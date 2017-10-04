package cn.zhengzhaoyu.personalPage.email;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class EmailValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        validateEmail("address","message","邮箱格式错误");
    }

    @Override
    protected void handleError(Controller c) {
        c.setAttr("status",false);
        c.renderJson();
    }
}
