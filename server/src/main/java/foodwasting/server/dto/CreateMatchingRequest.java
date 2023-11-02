package foodwasting.server.dto;

import lombok.Data;

@Data
public class CreateMatchingRequest {

    private Long memberId;
    private Boolean trashSize;
    private Boolean trashOwn;
    private String address;

    public CreateMatchingRequest(Long memberId, Boolean trashSize, Boolean trashOwn, String address) {
        this.memberId = memberId;
        this.trashSize = trashSize;
        this.trashOwn = trashOwn;
        this.address = address;
    }
}
