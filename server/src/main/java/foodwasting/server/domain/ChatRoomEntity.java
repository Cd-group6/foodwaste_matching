package foodwasting.server.domain;

import foodwasting.server.dto.ChatRoomDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "chatRoom_table")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomId;

    @Column
    private String roomName;

    @JoinColumn(name = "member1_id")
    private String member1;

    @JoinColumn(name = "member2_id")
    private String member2;

    @JoinColumn(name = "member3_id")
    private String member3;

    @JoinColumn(name = "member1_name")
    private String member1Name;

    @JoinColumn(name = "member2_name")
    private String member2Name;

    @JoinColumn(name = "member3_name")
    private String member3Name;

    @Column
    private String adress;

    /*
    @OneToMany(mappedBy = "chatRoomEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();

     */

    public static ChatRoomEntity toChatRoomEntity(ChatRoomDTO chatRoomDTO){
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();

        chatRoomEntity.setRoomName(chatRoomDTO.getRoomName());
        chatRoomEntity.setRoomId(chatRoomDTO.getRoomId());
        chatRoomEntity.setMember1(chatRoomDTO.getMember1());
        chatRoomEntity.setMember2(chatRoomDTO.getMember2());
        chatRoomEntity.setMember3(chatRoomDTO.getMember3());

        chatRoomEntity.setMember1Name(chatRoomDTO.getMember1Name());
        chatRoomEntity.setMember2Name(chatRoomDTO.getMember2Name());
        chatRoomEntity.setMember3Name(chatRoomDTO.getMember3Name());
        chatRoomEntity.setAdress(chatRoomDTO.getAdress());

        return chatRoomEntity;
    }

}
