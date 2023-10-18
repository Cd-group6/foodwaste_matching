package foodwasting.server.dto;

import lombok.Data;

@Data
public class CreateMatchingResponse {

    private Long id;

    public CreateMatchingResponse(Long id) {
        this.id = id;
    }
}
