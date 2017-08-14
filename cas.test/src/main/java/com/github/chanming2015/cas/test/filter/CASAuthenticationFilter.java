package com.github.chanming2015.cas.test.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.jasig.cas.client.authentication.AuthenticationFilter;

/**
 * Description:
 * Create Date:2017年8月10日
 * @author XuMaoSen
 * Version:1.0.0
 */
@WebFilter(filterName = "CAS Authentication Filter", urlPatterns = "/authen/*", initParams = {
        @WebInitParam(name = "casServerLoginUrl", value = CASConfig.CASSERVERLOGINURL),
        @WebInitParam(name = "serverName", value = CASConfig.SERVERNAME)})
public class CASAuthenticationFilter extends AuthenticationFilter
{
}
