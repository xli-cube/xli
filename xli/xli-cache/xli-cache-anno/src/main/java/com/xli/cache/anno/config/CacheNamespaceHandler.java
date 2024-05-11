package com.xli.cache.anno.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CacheNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("annotation-driven", new CacheAnnotationParser());
    }
}
