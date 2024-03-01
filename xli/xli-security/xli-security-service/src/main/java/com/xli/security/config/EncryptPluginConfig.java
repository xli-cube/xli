package com.xli.security.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.xli.security.impl.AesSupport;
import com.xli.security.interceptor.DecryptReadInterceptor;
import com.xli.security.interceptor.SensitiveAndEncryptWriteInterceptor;
import com.xli.security.service.Encrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptPluginConfig {
    @Bean
    Encrypt encryptor() throws Exception {
        return new AesSupport("1234567890qwertyuiop");
    }

    @Bean
    ConfigurationCustomizer configurationCustomizer() throws Exception {
        DecryptReadInterceptor decryptReadInterceptor = new DecryptReadInterceptor(encryptor());
        SensitiveAndEncryptWriteInterceptor sensitiveAndEncryptWriteInterceptor = new SensitiveAndEncryptWriteInterceptor(encryptor());

        return (configuration) -> {
            configuration.addInterceptor(decryptReadInterceptor);
            configuration.addInterceptor(sensitiveAndEncryptWriteInterceptor);
        };
    }
}
