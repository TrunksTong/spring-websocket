package com.online.spring.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序入口
 * 
 * @author tongyufu
 *
 */
@ComponentScan(basePackages = "com.online.spring.websocket")
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        //会自动读取application.yml作为启动参数
        SpringApplication.run(Application.class);
    }

}
