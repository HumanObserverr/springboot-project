package com.miao.springdemo.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miao.springdemo.controller.CertificationController;
import com.miao.springdemo.interceptor.UserInterceptor;
import com.miao.springdemo.pojo.Message;
import com.miao.springdemo.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 三方代码参考后
 */
@Slf4j

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndPoint {
    private static Map<String, ChatEndPoint> onLineUsers = new ConcurrentHashMap<>();
    private Session session;
    private HttpSession httpSession;
    private String sessionId; // 用于存储会话标识符的变量名

    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("sessionId") String sessionId) {
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        System.out.println(session.getId() + "this is sessionObjet");
        this.httpSession = httpSession;
        this.sessionId = "user_" + session.getId(); // 生成唯一的会话标识符

        String username = (String) httpSession.getAttribute("username");
        sessionId = username;
        System.out.println("onOpen" + username);
        System.out.println("上线用户名称：" + username);

        // 判断用户是否已登录
        if (username != null) {
            onLineUsers.put(sessionId, this);
            String message = MessageUtils.getMessage(true, null, getNames());
            broadcastAllUsers(message);
        }
    }

    private Set<String> getNames() {
        return onLineUsers.keySet();
    }
//广播上线用户
    private void broadcastAllUsers(String message) {
        try {
            Set<String> names = onLineUsers.keySet();
            for (String name : names) {
                ChatEndPoint chatEndPoint = onLineUsers.get(name);
                chatEndPoint.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (session != null && session.isOpen()) { // 检查会话是否打开
                Set<String> names = onLineUsers.keySet();
                for (String name : names) {
                    ChatEndPoint chatEndPoint = onLineUsers.get(name);
                    if (chatEndPoint.session != null && chatEndPoint.session.isOpen()) { // 检查用户会话是否打开
                        chatEndPoint.session.getBasicRemote().sendText(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            String toName = mess.getToName();
            String data = mess.getMessage();
            String username = (String) httpSession.getAttribute("username");
            System.out.println(username + "向" + toName + "发送的消息：" + data);
            String resultMessage = MessageUtils.getMessage(false, username, data);
            if (StringUtils.hasLength(toName)) {
                onLineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {

           String username = (String) httpSession.getAttribute("username");
        System.out.println("用户离开：" + username);
        if (username != null) {
            // 只在退出登录时删除在线用户的 ID
            if (CertificationController.isLogoutFlag()) {
                onLineUsers.remove(sessionId);
                UserInterceptor.onLineUsers.remove(username);
            }
        }
        httpSession.removeAttribute("username");
        String message = MessageUtils.getMessage(true, null, getNames());
        broadcastAllUsers(message);

    }
}
