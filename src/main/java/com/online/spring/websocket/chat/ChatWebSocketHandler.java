package com.online.spring.websocket.chat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 聊天室
 * 
 * @author tongyufu
 * 
 */
@Service
public class ChatWebSocketHandler extends TextWebSocketHandler {

	private static final AtomicInteger userIds = new AtomicInteger(0);
	private final Map<WebSocketSession, UserInfo> users = new ConcurrentHashMap<WebSocketSession, UserInfo>();
	@Autowired
	private ChatService chatService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		UserInfo user = users.get(session);
		if (user == null) {
			Integer userId = userIds.getAndIncrement();
			user = new UserInfo(userId, "Guest" + userId);
			users.put(session, user);
		}
		chatService.setUsers(users);
		String message = chatService.buildMessage("message", String.format("[%s]进入了聊天室", user.getName()));
		chatService.broadcast(message);
		chatService.broadcast(chatService.buildMessage("users", chatService.getNames()));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
		UserInfo user = users.get(session);
		String message = String.format("[%s]说：%s", user.getName(), textMessage.getPayload());
		chatService.broadcast(chatService.buildMessage("message", message));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		UserInfo user = users.remove(session);
		String message = chatService.buildMessage("message", String.format("[%s]离开了聊天室", user.getName()));
		chatService.broadcast(message);

	}

}
