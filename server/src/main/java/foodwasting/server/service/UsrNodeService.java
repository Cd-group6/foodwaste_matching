package foodwasting.server.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Getter

public class UsrNodeService {
    Long uId;
    Double[] axes;


    protected UsrNodeService(){
    }
    public UsrNodeService(Long uId, Double[] arr) {
        this.uId = uId;
        this.axes = arr;
    }


    public Long getUId() {
        return this.uId;
    }
}
