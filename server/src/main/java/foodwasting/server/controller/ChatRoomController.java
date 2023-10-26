package foodwasting.server.controller;

import foodwasting.server.domain.ChatMessageEntity;
import foodwasting.server.domain.ChatRoomEntity;
import foodwasting.server.dto.ChatRoomDTO;
import foodwasting.server.repository.ChatMessageRepository;
import foodwasting.server.repository.ChatRoomRepository;
import foodwasting.server.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatRoom")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService cs;

    @PostMapping("/roomtest")
    public ChatRoomDTO testMatch(@RequestParam String member1, @RequestParam String member2, @RequestParam String member3){
        String randomRoomId = UUID.randomUUID().toString();

        String name = member1 + "님의 방";

        ChatRoomDTO room = ChatRoomDTO.builder()
                .roomId(randomRoomId)
                .roomName(name)
                .member1(member1)
                .member2(member2)
                .member3(member3)
                .build();

        return cs.createChatRoom(member1, member2, member3);
    }

    @PostMapping("/checkmatched")
    public List<ChatRoomEntity> testMatched(@RequestParam String myName) {
        List<ChatRoomEntity> resultMatched = null;
        if (chatRoomRepository.findByMember1(myName) != null) {
            resultMatched =chatRoomRepository.findByMember1(myName);
        }
        if (chatRoomRepository.findByMember2(myName) != null) {
            resultMatched =chatRoomRepository.findByMember2(myName);
        }
        if (chatRoomRepository.findByMember3(myName) != null) {
            resultMatched = chatRoomRepository.findByMember3(myName);
        }
        return resultMatched = chatRoomRepository.findByMember1(myName);
    }

    @PostMapping("/rooms/{MemberName}")
    public List<ChatRoomEntity> rooms(@RequestParam String MemberName){
        List<ChatRoomEntity> result = null;
        if(chatRoomRepository.findByMember1(MemberName) != null){
            result = chatRoomRepository.findByMember1(MemberName);
        }
        if(chatRoomRepository.findByMember2(MemberName) != null){
            result = chatRoomRepository.findByMember2(MemberName);
        }
        if(chatRoomRepository.findByMember3(MemberName) != null){
            result = chatRoomRepository.findByMember3(MemberName);
        }
        return result;
    }

    @PostMapping("/chat")
    @ResponseBody
    public List<ChatMessageEntity> recallMessage(@RequestParam String roomId) {
        return chatMessageRepository.findAllByRoomId(roomId);
    }

}