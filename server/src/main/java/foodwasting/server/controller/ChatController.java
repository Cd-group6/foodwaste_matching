package foodwasting.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import foodwasting.server.dto.ChatRoom;
import foodwasting.server.service.ChatService;

import java.util.List;

// 경로/chat으로 설정함 근데? ws/localhost:8080/chat이 경로
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService service;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return service.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRooms() {
        return service.findAllRoom();
    }
}