package foodwasting.server.dto;

import lombok.Data;

@Data
public class CreateMatchingRequest {

    private Long memberId;
    private Boolean trashSize;
    private Boolean trashOwn;
    private String address;
}
