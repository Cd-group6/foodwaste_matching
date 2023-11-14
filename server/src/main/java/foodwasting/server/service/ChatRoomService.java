package foodwasting.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import foodwasting.server.domain.ChatMessageEntity;
import foodwasting.server.domain.ChatRoomEntity;
import foodwasting.server.domain.Member;
import foodwasting.server.domain.MessageType;
import foodwasting.server.dto.ChatRoomDTO;
import foodwasting.server.dto.MessageDTO;
import foodwasting.server.repository.ChatMessageRepository;
import foodwasting.server.repository.ChatRoomRepository;
import foodwasting.server.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private Map<String, ChatRoomDTO> chatRoomDTOMap;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    private final DepositService depositService;

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomDTO> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public ChatRoomDTO findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }



    public ChatRoomDTO createChatRoom(String member1, String member2, String member3, String member1Name, String member2Name, String member3Name, String adress) {
        String randomRoomId = UUID.randomUUID().toString();

        String name = member1 + "님의 방";

        //서창호가 우용이 노트북으로 추가
        Member memberf = memberRepository.findById(Long.parseLong(member1)).get();
        memberf.setDeposit(memberf.getDeposit() - 900);
        Member members = memberRepository.findById(Long.parseLong(member2)).get();
        members.setDeposit(members.getDeposit() - 900);
        Integer setdeposit = 1800;
        ChatRoomDTO room = ChatRoomDTO.builder()
                .roomId(randomRoomId)
                .roomName(name)
                .member1(member1)
                .member2(member2)
                .member3(member3)
                .member1Name(member1Name)
                .member2Name(member2Name)
                .member3Name(member3Name)
                .adress(adress)
                .build();
        room.setDeposit(1800);
        chatRooms.put(room.getRoomId(), room);

        // DB save
        ChatRoomEntity roomintoDB = ChatRoomEntity.toChatRoomEntity(room);
        chatRoomRepository.save(roomintoDB);

        MessageDTO joinMessage = MessageDTO.builder()
                .type(MessageType.ENTER)
                .roomId(room.getRoomId())
                .sender("adress")
                .senderName("ADRESS")
                .message("쓰레기통 주소는" + adress)
                .build();
        ChatMessageEntity joinMessageSave = ChatMessageEntity.toChatMessageEntity(joinMessage);
        chatMessageRepository.save(joinMessageSave);

        return room;
    }
}
