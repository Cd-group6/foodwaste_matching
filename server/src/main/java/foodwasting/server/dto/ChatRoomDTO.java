package foodwasting.server.dto;

import lombok.*;

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
}
