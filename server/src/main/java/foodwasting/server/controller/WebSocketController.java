package foodwasting.server.controller;

import foodwasting.server.dto.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {
    /*
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chatting")
    public void sendMessage(ChatDTO chatDTO, SimpMessageHeaderAccessor accessor  ) {
        simpMessagingTemplate.convertAndSend("/sub/chatting" + chatDTO.getRoomId(), chatDTO);
    }

     */
}
