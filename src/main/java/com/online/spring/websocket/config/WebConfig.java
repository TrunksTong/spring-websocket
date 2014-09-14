package com.online.spring.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.online.spring.websocket.chat.ChatClusterhandler;
import com.online.spring.websocket.chat.ChatWebSocketHandler;
import com.online.spring.websocket.echo.DefaultEchoService;
import com.online.spring.websocket.echo.EchoWebSocketHandler;

/**
 * <pre>
 * Spring MVC配置。
 * WebMvcConfigurerAdapter：定义回调方法来定制基于注解的Spring MVC，需要注解@EnableWebMvc。
 * WebSocketConfigurer：定义回调方法来配置WebSocket，需要注解@EnableWebMvc。
 * </pre>
 * 
 * @author tongyufu
 * 
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
@ImportResource(value = { "classpath:/spring-mvc.xml" })
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;
    @Autowired
    private ChatClusterhandler   chatClusterhandler;

    /**
     * 注册WebSocket处理程序，包含SockJS。<br />
     * <br />
     * {@inheritDoc}
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 原生 WebSocket
        registry.addHandler(echoWebSocketHandler(), "/echo", "/echo-issue4");
        // SockJS
        registry.addHandler(echoWebSocketHandler(), "/sockjs/echo").withSockJS();
        registry.addHandler(echoWebSocketHandler(), "/sockjs/echo-issue4").withSockJS()
            .setHttpMessageCacheSize(20000);

        registry.addHandler(chatWebSocketHandler, "/sockjs/chat").withSockJS();
        registry.addHandler(chatClusterhandler, "/sockjs/cluster").withSockJS();
    }

    @Bean
    public WebSocketHandler echoWebSocketHandler() {
        return new EchoWebSocketHandler(echoService());
    }

    @Bean
    public DefaultEchoService echoService() {
        return new DefaultEchoService("did you say \"%s\"?");
    }

    /**
     * 配置一个默认Servlet处理程序，一般是用来处理静态资源。<br />
     * <br />
     * {@inheritDoc}
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
