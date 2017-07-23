package cn.zhengzhaoyu.personalPage.comment;

import cn.zhengzhaoyu.personalPage.common.validator.BaseValidator;
import com.jfinal.core.Controller;

public class CommentValidator extends BaseValidator {
    @Override
    protected void validate(Controller c) {
        validateString("name",1,30,"message","输入id无效.");
    }

    @Override
    protected void handleError(Controller c) {
        c.setAttr("status",false);
        c.renderJson();
    }
}
