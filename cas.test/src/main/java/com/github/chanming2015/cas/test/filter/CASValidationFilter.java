package com.github.chanming2015.cas.test.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;

/**
 * Description:
 * Create Date:2017年8月10日
 * @author XuMaoSen
 * Version:1.0.0
 */
@WebFilter(filterName = "CAS Validation Filter", urlPatterns = "/authen/*", initParams = {
        @WebInitParam(name = "casServerUrlPrefix", value = CASConfig.CASSERVERURLPREFIX),
        @WebInitParam(name = "serverName", value = CASConfig.SERVERNAME)})
public class CASValidationFilter extends Cas20ProxyReceivingTicketValidationFilter
{
}
