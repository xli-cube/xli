package com.xli.security.type.handler;

import com.xli.security.type.SensitiveType;
import com.xli.security.type.SensitiveTypeHandler;
import com.xli.security.utils.StringUtils;

/**
 * 签约协议号脱敏方式
 * 11234317275678051111
 * 签约协议号脱敏格式为前6位后6位保留明文，中间脱敏
 */
public class PaySignNoSensitiveHandler implements SensitiveTypeHandler {

    @Override
    public SensitiveType getSensitiveType() {
        return SensitiveType.PAY_SIGN_NO;
    }

    @Override
    public String handle(Object src) {
        if (src == null) {
            return null;
        }
        String agreementNo = src.toString();
        return StringUtils.left(agreementNo, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(agreementNo, 6), StringUtils.length(agreementNo), "*"), "***"));


    }
}
