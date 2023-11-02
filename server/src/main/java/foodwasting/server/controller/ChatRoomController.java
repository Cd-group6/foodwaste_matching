package foodwasting.server.controller;

import foodwasting.server.domain.ChatMessageEntity;
import foodwasting.server.domain.ChatRoomEntity;
import foodwasting.server.dto.ChatRoomDTO;
import foodwasting.server.repository.ChatMessageRepository;
import foodwasting.server.repository.ChatRoomRepository;
import foodwasting.server.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;



@RestController
@RequiredArgsConstructor
@RequestMapping("/chatRoom")
@Slf4j
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

    @PostMapping("/recall")
    public List<ChatMessageEntity> recallmessage(@RequestParam String roomId) {
        List<ChatMessageEntity> recallm = chatMessageRepository.findAllByRoomId(roomId);
        return recallm;
    }

    @PostMapping("/checkmatched")
    public List<ChatRoomEntity> testMatched(@RequestParam String myName) {
        List<ChatRoomEntity> resultMatched = null;
        if (!chatRoomRepository.findByMember1(myName).isEmpty()) {
            log.info("if 1");
            return resultMatched =chatRoomRepository.findByMember1(myName);
        }
        else if (!chatRoomRepository.findByMember2(myName).isEmpty()) {
            log.info("if 2");
            return resultMatched =chatRoomRepository.findByMember2(myName);
        }
        else if (!chatRoomRepository.findByMember3(myName).isEmpty()) {
            log.info("if 3");
            return resultMatched = chatRoomRepository.findByMember3(myName);
        }
        return resultMatched;
    }

    @PostMapping("/rooms/{MemberName}")
    public List<ChatRoomEntity> rooms(@RequestParam String MemberName){
        List<ChatRoomEntity> result = null;
        if(!chatRoomRepository.findByMember1(MemberName).isEmpty()){
            result = chatRoomRepository.findByMember1(MemberName);
        }
        if(!chatRoomRepository.findByMember2(MemberName).isEmpty()){
            result = chatRoomRepository.findByMember2(MemberName);
        }
        if(chatRoomRepository.findByMember3(MemberName).isEmpty()){
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