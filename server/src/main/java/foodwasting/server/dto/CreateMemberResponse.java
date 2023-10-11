package foodwasting.server.dto;

import lombok.Data;
import lombok.extern.java.Log;

@Data
public class CreateMemberResponse {

    private Long id;

    public CreateMemberResponse(Long id) {
        this.id = id;
    }
}
