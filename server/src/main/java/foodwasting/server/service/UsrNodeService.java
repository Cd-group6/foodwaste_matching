package foodwasting.server.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Getter
@RequiredArgsConstructor
public class UsrNodeService {
    Long uId;
    Long[] axes;

    public UsrNodeService(Long uId, Long[] arr) {
        this.uId = uId;
        this.axes = arr;
    }

    public Long getUId() {
        return this.uId;
    }
}
