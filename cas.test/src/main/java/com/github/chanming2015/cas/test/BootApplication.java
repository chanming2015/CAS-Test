package com.github.chanming2015.cas.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Description:
 * Create Date:2017年8月10日
 * @author XuMaoSen
 * Version:1.0.0
 */
@SpringBootApplication
@ServletComponentScan
public class BootApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BootApplication.class, args);
    }
}
