package foodwasting.server.controller;

import foodwasting.server.dto.DepositRequest;
import foodwasting.server.dto.DepositResponse;
import foodwasting.server.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DepositController {

    private final DepositService DepositService;

    @PostMapping("/api/deposit/charge")
    public DepositResponse chargeDeposit(@RequestBody DepositRequest request) {

        Integer deposit = DepositService.chargeDeposit(request);
        return new DepositResponse(deposit);
    }

    @PostMapping("/api/deposit/discharge")
    public DepositResponse dischargeDeposit(@RequestBody DepositRequest request) {

        Integer deposit = DepositService.dischargeDeposit(request);
        return new DepositResponse(deposit);
    }
}
