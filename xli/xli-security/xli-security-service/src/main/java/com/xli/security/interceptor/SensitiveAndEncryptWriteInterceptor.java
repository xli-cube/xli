package com.xli.security.interceptor;

import com.xli.security.annotation.*;
import com.xli.security.service.Encrypt;
import com.xli.security.type.SensitiveType;
import com.xli.security.type.SensitiveTypeRegisty;
import com.xli.security.utils.JsonUtils;
import com.xli.security.utils.PluginUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 拦截写请求的插件。插件生效仅支持预编译的sql
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),})
public class SensitiveAndEncryptWriteInterceptor implements Interceptor {

    private static final String MAPPED_STATEMENT = "delegate.mappedStatement";
    private static final String BOUND_SQL = "delegate.boundSql";

    private final Encrypt encrypt;

    public SensitiveAndEncryptWriteInterceptor(Encrypt encrypt) {
        Objects.requireNonNull(encrypt, "encrypt should not be null!");
        this.encrypt = encrypt;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(MAPPED_STATEMENT);
        SqlCommandType commandType = mappedStatement.getSqlCommandType();

        BoundSql boundSql = (BoundSql) metaObject.getValue(BOUND_SQL);
        Object params = boundSql.getParameterObject();
        if (params instanceof Map) {
            return invocation.proceed();
        }
        SensitiveEncryptEnabled sensitiveEncryptEnabled = params != null ? params.getClass().getAnnotation(SensitiveEncryptEnabled.class) : null;
        if (sensitiveEncryptEnabled != null && sensitiveEncryptEnabled.value()) {
            handleParameters(mappedStatement.getConfiguration(), boundSql, params, commandType);
        }
        return invocation.proceed();
    }

    private void handleParameters(Configuration configuration, BoundSql boundSql, Object param, SqlCommandType commandType) {
        Map<String, Object> newValues = new HashMap<>(16);
        MetaObject metaObject = configuration.newMetaObject(param);
        List<Field> fields = PluginUtils.getFields(param.getClass());
        for (Field field : fields) {
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
            if (encryptField != null || sensitiveField != null) {
                Object value = metaObject.getValue(field.getName());
                Object newValue = value;
                if (value instanceof CharSequence) {
                    newValue = handleEncryptField(field, newValue);
                    if (isWriteCommand(commandType) && !SensitiveTypeRegisty.alreadyBeSentisived(newValue)) {
                        newValue = handleSensitiveField(field, newValue);
                        newValue = handleSensitiveJSONField(field, newValue);
                    }
                }
                if (value != null && newValue != null && !value.equals(newValue)) {
                    newValues.put(field.getName(), newValue);
                }
            }
        }
        for (Map.Entry<String, Object> entry : newValues.entrySet()) {
            boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
        }

    }

    private boolean isWriteCommand(SqlCommandType commandType) {
        return SqlCommandType.UPDATE.equals(commandType) || SqlCommandType.INSERT.equals(commandType);
    }

    private Object handleEncryptField(Field field, Object value) {
        EncryptField encryptField = field.getAnnotation(EncryptField.class);
        Object newValue = value;
        if (encryptField != null && value != null) {
            newValue = encrypt.encrypt(value.toString());
        }
        return newValue;
    }

    private Object handleSensitiveField(Field field, Object value) {
        SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
        Object newValue = value;
        if (sensitiveField != null && value != null) {
            newValue = SensitiveTypeRegisty.get(sensitiveField.value()).handle(value);
        }
        return newValue;
    }

    private Object handleSensitiveJSONField(Field field, Object value) {
        SensitiveJSONField sensitiveJSONField = field.getAnnotation(SensitiveJSONField.class);
        Object newValue = value;
        if (sensitiveJSONField != null && value != null) {
            newValue = processJsonField(newValue, sensitiveJSONField);
        }
        return newValue;
    }

    /**
     * 在json中进行脱敏
     *
     * @param newValue           new
     * @param sensitiveJSONField 脱敏的字段
     * @return json
     */
    private Object processJsonField(Object newValue, SensitiveJSONField sensitiveJSONField) {

        try {
            Map<String, Object> map = JsonUtils.parseToObjectMap(newValue.toString());
            SensitiveJSONFieldKey[] keys = sensitiveJSONField.sensitivelist();
            for (SensitiveJSONFieldKey jsonFieldKey : keys) {
                String key = jsonFieldKey.key();
                SensitiveType sensitiveType = jsonFieldKey.type();
                Object oldData = map.get(key);
                if (oldData != null) {
                    String newData = SensitiveTypeRegisty.get(sensitiveType).handle(oldData);
                    map.put(key, newData);
                }
            }
            return JsonUtils.parseMaptoJSONString(map);
        } catch (Throwable e) {
            //失败以后返回默认值
            log.error("脱敏json串时失败，cause : {}", e.getMessage(), e);
            return newValue;
        }
    }
}
