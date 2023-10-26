package foodwasting.server.domain;

import foodwasting.server.dto.MessageDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "chat_Table")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")

     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoomEntity chatRoomEntity;

    @Column
    private String roomId;

    @Column
    private MessageType messageType;

    private String sender;

    @Column
    private String message;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime sendDate;

    public static ChatMessageEntity toChatMessageEntity(MessageDTO messageDTO){
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();

        //messageId plz

        chatMessageEntity.setMessageType(messageDTO.getType());
        chatMessageEntity.setRoomId(messageDTO.getRoomId());
        chatMessageEntity.setSender(messageDTO.getSender());
        chatMessageEntity.setMessage(messageDTO.getMessage());

        return chatMessageEntity;
    }


}
