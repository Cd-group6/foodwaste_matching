package foodwasting.server.dto;

import foodwasting.server.domain.MessageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDTO {
    // 메세지 : 입장, 채팅 들어가는 json 형식


    private MessageType type; //ENTER 냐 TALK 냐 상황 구분
    private String roomId; //service.Chatservice.java에서 난수 설정해줌
    private String sender; //채팅 보낸사람 ID
    private String message;
    private String time;
}