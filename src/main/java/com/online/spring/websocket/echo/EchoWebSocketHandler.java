package com.online.spring.websocket.echo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Echo messages by implementing a Spring {@link WebSocketHandler} abstraction.
 */
public class EchoWebSocketHandler extends TextWebSocketHandler {

	private final EchoService echoService;

	@Autowired
	public EchoWebSocketHandler(EchoService echoService) {
		this.echoService = echoService;
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String reply = this.echoService.getMessage(message.getPayload());
		session.sendMessage(new TextMessage(reply));
	}

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("新连接加入：" + session.getId());
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
                                                                                   throws Exception {
        System.out.println("连接断开：" + session.getId());
        super.afterConnectionClosed(session, status);
    }

}
