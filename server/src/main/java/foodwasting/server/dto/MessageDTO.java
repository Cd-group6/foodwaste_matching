package foodwasting.server.dto;

import foodwasting.server.domain.MessageType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
public class MessageDTO {


    //message 고유 Id 부재
    private MessageType type;
    private String roomId;
    private String sender;

    private String senderName;

    private String message;
    private LocalDateTime sendTime;
}
