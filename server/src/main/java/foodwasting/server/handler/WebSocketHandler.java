package foodwasting.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import foodwasting.server.dto.MessageDTO;
import foodwasting.server.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    //yo lang
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private static List<WebSocketSession> list = new ArrayList<>();


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);

        //yolang
        MessageDTO messageDTO = objectMapper.readValue(payload, MessageDTO.class);

        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }


    // client 세션 접속 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info(session + "success access");
    }

    // client 접속 해제
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + "access disconnected");
        list.remove(session);
    }
}
