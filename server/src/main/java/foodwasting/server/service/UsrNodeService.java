package foodwasting.server.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Getter

public class UsrNodeService {
    Long uId, timeStamp;
    Double[] axes;


    public Long getTimestamp(){
        return this.timeStamp;
    }

    protected UsrNodeService(){
    }
    public UsrNodeService(Long uId, Double[] arr, Long timeStamp) {
        this.uId = uId;
        this.axes = arr;
        this.timeStamp = timeStamp;
    }


    public Long getUId() {
        return this.uId;
    }
}
