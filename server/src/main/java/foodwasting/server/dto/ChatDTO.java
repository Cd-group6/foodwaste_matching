package foodwasting.server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDTO {
    // 메세지 : 입장, 채팅 들어가는 json 형식
    public enum  MessageType{
        ENTER, TALK
    }

    private MessageType type; //
    private String roomId;
    private String sender; //채팅 보낸사람 ID
    private String message;
    private String time;
}