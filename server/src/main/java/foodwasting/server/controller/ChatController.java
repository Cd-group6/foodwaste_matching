package foodwasting.server.controller;

import foodwasting.server.domain.ChatMessageEntity;
import foodwasting.server.dto.MessageDTO;
import foodwasting.server.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import foodwasting.server.dto.ChatRoom;
import foodwasting.server.service.ChatService;

import java.util.List;

// 경로/chat으로 설정함 근데? ws/localhost:8080/chat이 경로
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate template;
    private final ChatMessageRepository chatMessageRepository;

    @MessageMapping(value = "/chat/message")
    public void message(MessageDTO message){

        ChatMessageEntity messageSave = ChatMessageEntity.toChatMessageEntity(message);
        chatMessageRepository.save(messageSave);
        template.convertAndSend("/sub/chatRoom/" + message.getRoomId(), message);
    }
}