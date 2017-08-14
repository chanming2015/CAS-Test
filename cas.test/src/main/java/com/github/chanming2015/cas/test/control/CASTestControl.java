package com.github.chanming2015.cas.test.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Create Date:2017年8月10日
 * @author XuMaoSen
 * Version:1.0.0
 */
@RestController
public class CASTestControl
{
    @GetMapping("/do_bussness")
    public String do_bussness()
    {
        return "without authentication access success";
    }

    @GetMapping("/authen/do_bussness")
    public String authen_do_bussness()
    {
        return "authentication access success";
    }
}
