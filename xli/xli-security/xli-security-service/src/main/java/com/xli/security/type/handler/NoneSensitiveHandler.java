package com.xli.security.type.handler;

import com.xli.security.type.SensitiveType;
import com.xli.security.type.SensitiveTypeHandler;

/**
 * 不脱敏
 */
public class NoneSensitiveHandler implements SensitiveTypeHandler {

    @Override
    public SensitiveType getSensitiveType() {
        return SensitiveType.NONE;
    }

    @Override
    public String handle(Object src) {
        if(src!=null){
            return src.toString();
        }
        return null;
    }
}
