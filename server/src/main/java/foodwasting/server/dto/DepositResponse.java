package foodwasting.server.dto;

import lombok.Data;

@Data
public class DepositResponse {

    private Integer deposit;

    public DepositResponse(Integer deposit) {
        this.deposit = deposit;
    }
}
