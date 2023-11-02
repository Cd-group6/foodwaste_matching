package foodwasting.server.dto;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private String roomId;
    private String roomName;
    private String member1;
    private String member2;
    private String member3;

    private String member1Name;
    private String member2Name;
    private String member3Name;
    private String adress;

    private final Set<WebSocketSession> sessions = new HashSet<>();
}
