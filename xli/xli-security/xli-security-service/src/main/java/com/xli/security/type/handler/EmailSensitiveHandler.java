package com.xli.security.type.handler;

import com.xli.security.type.SensitiveType;
import com.xli.security.type.SensitiveTypeHandler;
import com.xli.security.utils.StringUtils;

/**
 * 邮件信息脱敏处理类
 * 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示
 * 例子:1**@qq.com
 */
public class EmailSensitiveHandler implements SensitiveTypeHandler {

    @Override
    public SensitiveType getSensitiveType() {
        return SensitiveType.EMAIL;
    }

    @Override
    public String handle(Object src) {
        if (src == null) {
            return null;
        }
        String email = src.toString();
        int index = StringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
        }
    }
}
