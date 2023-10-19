package foodwasting.server.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Getter
@Service
public class NodeService implements Comparable<NodeService> {
    long[] axes;
    NodeService left, right; // left, right, parent
    long d;
    int idx, state; // root-axes distance , 방문한 분기점 idx 0 = left, 1 = right, 2 = leaf 노드
    ArrayList<NodeService> group = new ArrayList<NodeService>();

    public NodeService(long[] arr) {
        this.axes = arr;
        this.left = this.right = null;
        this.d = 999999999999l;
        this.state = 0; // 2-> match / others wait
    }

    public long getDistance() {
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
