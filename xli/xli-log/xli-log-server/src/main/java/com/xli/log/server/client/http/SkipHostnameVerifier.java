package com.xli.log.server.client.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 跳过Hostname检查
 *
 */
public class SkipHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }

}