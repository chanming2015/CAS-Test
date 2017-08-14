package com.github.chanming2015.cas.test.filter;

/**
 * Description:
 * Create Date:2017年8月11日
 * @author XuMaoSen
 * Version:1.0.0
 */
public interface CASConfig
{
    String CASSERVERLOGINURL = "https://cas.server.name:8443/cas/login";
    String CASSERVERURLPREFIX = "https://cas.server.name:8443/cas";
    /**
     *CAS Client URL, For CAS redirect after logined
     */
    String SERVERNAME = "http://cas.server.name:8080";
}
