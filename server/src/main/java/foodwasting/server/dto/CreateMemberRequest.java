package foodwasting.server.dto;

import lombok.Data;

@Data
public class CreateMemberRequest {

    private String name;
    private String email;
}
