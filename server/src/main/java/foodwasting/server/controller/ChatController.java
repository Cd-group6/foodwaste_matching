package foodwasting.server.controller;

import foodwasting.server.domain.ChatMessageEntity;
import foodwasting.server.dto.MessageDTO;
import foodwasting.server.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import foodwasting.server.dto.ChatRoom;
import foodwasting.server.service.ChatService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 경로/chat으로 설정함 근데? ws/localhost:8080/chat이 경로
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    //private final SimpMessagingTemplate template;
    private final ChatMessageRepository chatMessageRepository;

    private static Set<String> userList = new HashSet<>();

    /*
    @Autowired
    private final SimpMessagingTemplate template;



    @MessageMapping(value = "/message/{roomId}")
    public void message(@Payload MessageDTO message, @DestinationVariable String roomId){

        ChatMessageEntity messageSave = ChatMessageEntity.toChatMessageEntity(message);
        chatMessageRepository.save(messageSave);
        template.convertAndSend("/sub/chatRoom/" + roomId, message);
    }

     */

    @MessageMapping("/join")
    public void joinUser(@Payload String userId){
        userList.add(userId);
        userList.forEach(user-> System.out.println(user));
    }
}