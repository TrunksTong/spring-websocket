package com.online.spring.websocket.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import redis.clients.jedis.Jedis;

import com.online.spring.websocket.util.JsonUtil;
import com.online.spring.websocket.util.redis.JedisUtils;

/**
 * 聊天服务
 * 
 * @author tongyufu
 *
 */
@Service
public class ChatService {

    public static final String              CHANNEL = "chat.message";
    private Map<WebSocketSession, UserInfo> users;

    /**推送消息*/
    public void broadcast(String message) throws IOException {
        for (WebSocketSession session : users.keySet()) {
            session.sendMessage(new TextMessage(message));
        }
    }

    /**发布到redis*/
    public void publish(String message) {
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            jedis.publish(CHANNEL, message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String buildMessage(String type, String message) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", type);
        map.put("value", message);
        return JsonUtil.toJson(map);
    }

    public String getNames() {
        StringBuilder names = new StringBuilder("<b>在线用户</b><br>");
        for (UserInfo userInfo : users.values()) {
            names.append(userInfo.getName()).append("<br>");
        }
        return names.toString();
    }

    public void setUsers(Map<WebSocketSession, UserInfo> users) {
        this.users = users;
    }

}
