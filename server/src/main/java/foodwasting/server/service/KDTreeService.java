package foodwasting.server.service;

import org.springframework.stereotype.Service;

import java.lang.Long;
import java.lang.Integer;
import java.util.PriorityQueue;
import java.util.ArrayList;

@Service
public final class KDTreeService {
    static final Integer k = 2; // 2 dimensional
    // depth = 0

    private NodeService newNode(Double[] arr, Long uId) {
        NodeService node = new NodeService(arr);
        node.uId = uId;
        return node;
    }

    public NodeService insert(NodeService root, Double[] axes, Long uId) {
        return insertTr(root, axes, uId, 0l);
    }

    private NodeService insertTr(NodeService root, Double[] axes, Long uId, Long depth) {
        if (root == null) { // if tree is empty
            return newNode(axes, uId);
        } else {
            Integer cd = Math.toIntExact(depth % k); // 0 or 1 -> x or y

            if (axes[cd] < root.axes[cd]) { // insert node go right when larger than root node
                root.left = insertTr(root.left, axes, uId, depth + 1);
            } else {
                root.right = insertTr(root.right, axes, uId, depth + 1);
            }

            return root;
        }
    }

    private boolean areaxesSame(Double[] axes1, Double[] axes2) {

        for (Integer i = 0; i < k; i++) {
            if (axes1[i] != axes2[i]) {
                return false;
            }
        }
        return true;
    }

    private NodeService findMin(NodeService root, Integer d) {
        return findMinRec(root, d, 0l);
        // d means dimension
    }

    private NodeService findMinRec(NodeService root, Integer d, Long depth) {
        if (root == null) {
            return null;
        }

        Integer cd = Math.toIntExact(depth % k);

        if (cd == d) { // then min rec may be in left sub tree
            if (root.left == null) {
                return root;
            }

            return findMinRec(root.left, d, depth + 1);
        }

        // else have to compare all
        return minNode(root, findMinRec(root.left, d, depth + 1), findMinRec(root.right, d, depth + 1), d);
    }

    private NodeService minNode(NodeService a, NodeService b, NodeService c, Integer d) {
        NodeService min = a;

        if (b != null && (b.axes[d] < min.axes[d])) {
            min = b;
        }
        if (c != null && (c.axes[d] < min.axes[d])) {
            min = c;
        }

        return min;
    }

    private NodeService deleteNode(NodeService root, Double[] axes) {
        return deleteNodeRec(root, axes, 0l);
    }

    private NodeService deleteNodeRec(NodeService root, Double[] axes, Long depth) {
        if (root == null) {
            return null;
        }

        Integer cd = Math.toIntExact(depth % k);

        if (areaxesSame(root.axes, axes)) {
            if (root.right != null) { // find min in right sub tree
                NodeService min = findMin(root.right, cd);
                root = min;
                root.right = deleteNodeRec(root.right, min.axes, depth + 1);
            } else if (root.left != null) { // find min in left sub tree
                NodeService min = findMin(root.left, cd);
                root = min;
                root.right = deleteNodeRec(root.left, min.axes, depth + 1);
            } else { // just delete it
                root = null;
            }
            return root;
        }

        if (axes[cd] < root.axes[cd]) {
            root.left = deleteNodeRec(root.left, axes, depth + 1);
        } else {
            root.right = deleteNodeRec(root.right, axes, depth + 1);
        }

        return root;

    }

    private double haversine(Double lat1, Double lon1, Double lat2, Double lon2) {
        // 위도와 경도를 라디안으로 변환
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        System.out.println("l1 : " + lat1);
        System.out.println("l1 : " + lat2);
        System.out.println("l1 : " + lon1);
        System.out.println("l1 : " + lon2);
        // Haversine 공식 계산
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // 지구의 반지름 (킬로미터)
        double R = 6371.0;
        double distance = R * c;

        return distance;
    }

    private NodeService searchNode(NodeService root, NodeService best, Double[] axes, Long depth, PriorityQueue<NodeService> q) {
        root.idx = 2; // it means leaf
        root.d = haversine(root.axes[0], root.axes[1], axes[0], axes[1]);
        q.add(root);

        if (best.d > root.d) { // root -> best
            best = root;
        }

        if (root.d == 0) {
            return best;
        }

        Integer cd = Math.toIntExact(depth % k);

        // move to the next node
        if (axes[cd] < root.axes[cd]) { // left
            if (root.left == null) { // current node != leaf
                return best;
            }
            root.idx = 0;
            return searchNode(root.left, best, axes, depth + 1, q);

        } else { // right
            if (root.right == null) {
                return best;
            }
            root.idx = 1;
            return searchNode(root.right, best, axes, depth + 1, q);
        }

    }

    public NodeService nearest(NodeService root, Double[] axes) {
        if (root == null) {
            return null;
        }
        NodeService best = newNode(new Double[]{0.0, 0.0,}, 0l);
        PriorityQueue<NodeService> q = new PriorityQueue<>();

        // reset
        return nearestNeighbor(root, best, axes, 0l, q);
    }

    private NodeService nearestNeighbor(NodeService root, NodeService best, Double[] axes, Long depth, PriorityQueue<NodeService> q) {
        Integer cd = Math.toIntExact(depth % k);

        best = searchNode(root, best, axes, depth, q); // search로 들어가면서 axes와의 distance를 계산해 저장해 그것을 기반으로 queue에 저장

        if (best.uId == 0l) {
            return null;
        }

        while (!q.isEmpty()) { // 역으로 올라가기
            NodeService node = q.poll();

            if (node.d <= 2) { // stop
                best = node;
                break;
            }

            // non visited branch
            if (node.idx == 0) {
                if (Math.pow((double) axes[cd] - (double) node.axes[cd], 2) <= best.d) {
                    if (node.right == null) {
                        continue; // there's nothing to visit,, next poll
                    }
                    best = nearestNeighbor(node.right, best, axes, depth, q);
                }
            } else if (node.idx == 1) {
                if (Math.pow((double) axes[cd] - (double) node.axes[cd], 2) <= best.d) {
                    if (node.left == null) {
                        continue;
                    }
                    best = nearestNeighbor(node.left, best, axes, depth, q);
                }
            }
        }
        return best;
    }

    public ArrayList<UsrNodeService> findGroup(NodeService root, NodeService best, UsrNodeService s) {
        if ((best != null) && (best.state <= 2)) {
            best.state++;
            best.group.add(s);
            if (best.state == 2) {
                deleteNode(root, best.axes);
                return best.group;
            }

        }
        return null;
    }
}