package foodwasting.server.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.lang.Long;
import java.lang.Integer;

import java.util.ArrayList;

@Getter
@Service
public class NodeService implements Comparable<NodeService> {
    Double[] axes;
    NodeService left, right; // left, right, parent
    Long uId;
    double d;
    Integer idx, state; // root-axes distance , 방문한 분기점 idx 0 = left, 1 = right, 2 = leaf 노드
    ArrayList<UsrNodeService> group = new ArrayList<UsrNodeService>();

    protected NodeService(Double[] arr) {
        this.axes = arr;
        this.left = this.right = null;
        this.d = 999999999999l;
        this.state = 0; // 2-> match / others wait
    }

    public double getDistance() {
        return this.d;
    }

    @Override
    public int compareTo(NodeService node) {
        if (this.d > node.getDistance()) {
            return 1;
        } else if (this.d < node.getDistance()) {
            return -1;
        }
        return 0;
    }
}

