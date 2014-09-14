package com.online.spring.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * applicationContext配置
 * 
 * @Configuration：必须，有此注解，Spring才会扫描此类
 * @ImportResource：引入spring配置文件，相当于<import/>
 * 
 * @author tongyufu
 *
 */
@Configuration
@ImportResource(value = { "classpath:/applicationContext.xml" })
public class RootConfig {

}
