package com.online.spring.websocket.chat;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import com.online.spring.websocket.util.redis.JedisUtils;

/**
 * 订阅消息
 * 
 * @author tongyufu
 *
 */
@Service
public class Subscribe {

    /**
     * 使用注入通过构造函数注入，@Qualifier必须指定Bean名字。
     */
    @Autowired
    public Subscribe(@Qualifier("chatService") final ChatService chatService) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Jedis jedis = JedisUtils.getJedis();

                jedis.subscribe(new JedisPubSub() {

                    @Override
                    public void onUnsubscribe(String channel, int subscribedChannels) {
                        System.out.println("onUnsubscribe:" + channel);
                    }

                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {
                        System.out.println("onSubscribe:" + channel);
                    }

                    @Override
                    public void onPUnsubscribe(String pattern, int subscribedChannels) {
                        System.out.println("onPUnsubscribe:" + pattern);
                    }

                    @Override
                    public void onPSubscribe(String pattern, int subscribedChannels) {
                        System.out.println("onPSubscribe:" + pattern);
                    }

                    @Override
                    public void onPMessage(String pattern, String channel, String message) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.print("onMessage:" + channel);
                        System.out.println("\t" + message);
                        try {
                            chatService.broadcast(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, ChatService.CHANNEL);
            }
        }).start();;
    }

}
