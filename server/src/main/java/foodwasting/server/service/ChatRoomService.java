package foodwasting.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import foodwasting.server.domain.ChatMessageEntity;
import foodwasting.server.domain.ChatRoomEntity;
import foodwasting.server.domain.MessageType;
import foodwasting.server.dto.ChatRoomDTO;
import foodwasting.server.dto.MessageDTO;
import foodwasting.server.repository.ChatMessageRepository;
import foodwasting.server.repository.ChatRoomRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private Map<String, ChatRoomDTO> chatRoomDTOMap;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomDTO> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public ChatRoomDTO findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }



    public ChatRoomDTO createChatRoom(String member1, String member2, String member3) {
        String randomRoomId = UUID.randomUUID().toString();

        String name = member1 + "님의 방";

        ChatRoomDTO room = ChatRoomDTO.builder()
                .roomId(randomRoomId)
                .roomName(name)
                .member1(member1)
                .member2(member2)
                .member3(member3)
                .build();
        chatRooms.put(room.getRoomId(), room);

        // DB save
        ChatRoomEntity roomintoDB = ChatRoomEntity.toChatRoomEntity(room);
        chatRoomRepository.save(roomintoDB);

        MessageDTO joinMessage = MessageDTO.builder()
                .type(MessageType.ENTER)
                .roomId(room.getRoomId())
                .sender("join comment")
                .message(name + "에 입장하셨습니다")
                .build();
        ChatMessageEntity joinMessageSave = ChatMessageEntity.toChatMessageEntity(joinMessage);
        chatMessageRepository.save(joinMessageSave);

        return room;
    }
}
