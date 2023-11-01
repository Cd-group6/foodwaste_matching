package foodwasting.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import foodwasting.server.domain.ChatRoomEntity;
import foodwasting.server.domain.MessageType;
import foodwasting.server.dto.ChatDTO;
import foodwasting.server.dto.ChatRoomDTO;
import foodwasting.server.dto.MessageDTO;
import foodwasting.server.model.ChatMessage;
import foodwasting.server.repository.ChatRoomRepository;
import foodwasting.server.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    //yo lang
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private static List<WebSocketSession> list = new ArrayList<>();

    private final ChatRoomService chatRoomService;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);

        //yolang
        MessageDTO messageDTO = objectMapper.readValue(payload, MessageDTO.class);
        ChatRoomDTO room = chatRoomService.findRoomById(messageDTO.getRoomId());

        Set<WebSocketSession> sessions = room.getSessions();
        if (messageDTO.getType().equals(MessageType.ENTER)) {
            sessions.add(session);
            messageDTO.setMessage(messageDTO.getSender() + "is enter");

            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(messageDTO)));

        }else if (messageDTO.getType().equals(MessageType.QUIT)) {
            sessions.remove(session);
            messageDTO.setMessage(messageDTO.getSender() + "is QUIT");
            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(messageDTO)));
        }else {
            sendToEachSocket(sessions,message);
        }
    }
    private void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message) {
        sessions.parallelStream().forEach( roomSession ->{
            try {
                roomSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }




    // client 세션 접속 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //레퍼에선 아래 두개 지웠음
        //list.add(session);
        log.info(session + "success access");
    }

    // client 접속 해제
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + "access disconnected");
        //list.remove(session);
    }
}
