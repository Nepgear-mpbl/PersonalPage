package cn.zhengzhaoyu.personalPage.common.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public abstract class BaseValidator extends Validator {
    public BaseValidator() {
        shortCircuit=true;
    }
}
