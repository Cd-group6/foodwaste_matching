package foodwasting.server.dto;

import foodwasting.server.domain.MessageType;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import foodwasting.server.service.ChatService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;



@Data
public class ChatRoom {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatDTO message, ChatService service) {
        // message type 확인, get type에 따라 ENTER or TALK에 맞는 메세지 샌딩
        if (message.getType().equals(MessageType.ENTER)) {

            //sessions에 세션 추가
            sessions.add(session);
            //입장 메세지 출력
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            sendMessage(message, service);
        } else if (message.getType().equals(MessageType.TALK)) {
            message.setMessage(message.getMessage());
            sendMessage(message, service);
        }
    }

    public <T> void sendMessage(T message, ChatService service) {
        sessions.parallelStream().forEach(session -> service.sendMessage(session, message));
    }
}