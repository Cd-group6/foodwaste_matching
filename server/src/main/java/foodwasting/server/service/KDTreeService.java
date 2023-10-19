package foodwasting.server.service;
import org.springframework.scheduling.annotation.Async;
import org.w3c.dom.Node;

import java.util.PriorityQueue;
import java.util.ArrayList;

public class KDTreeService {
    int k = 2; // 2 dimensional
    // depth = 0

    public NodeService newNode(long[] arr) {
        return new NodeService(arr);
    }

    public NodeService insert(NodeService root, long[] axes) {
        return insertTr(root, axes, 0);
    }

    public NodeService insertTr(NodeService root, long[] axes, int depth) {
        if (root == null) { // if tree is empty
            return newNode(axes);
        } else {
            int cd = depth % k; // 0 or 1 -> x or y

            if (axes[cd] < root.axes[cd]) { // insert NodeService go right when larger than root node
                root.left = insertTr(root.left, axes, depth + 1);
            } else {
                root.right = insertTr(root.right, axes, depth + 1);
            }

            return root;
        }
    }

    public boolean areaxesSame(long[] axes1, long[] axes2) {

        for (int i = 0; i < k; i++) {
            if (axes1[i] != axes2[i]) {
                return false;
            }
        }
        return true;
    }

    public NodeService findMin(NodeService root, int d) {
        return findMinRec(root, d, 0);
        // d means dimension
    }

    public NodeService findMinRec(NodeService root, int d, int depth) {
        if (root == null) {
            return null;
        }

        int cd = depth % k;

        if (cd == d) { // then min rec may be in left sub tree
            if (root.left == null) {
                return root;
            }

            return findMinRec(root.left, d, depth + 1);
        }

        // else have to compare all
        return minNode(root, findMinRec(root.left, d, depth + 1), findMinRec(root.right, d, depth + 1), d);
    }

    public NodeService minNode(NodeService a, NodeService b, NodeService c, int d) {
        NodeService min = a;

        if (b != null && b.axes[d] < min.axes[d]) {
            min = b;
        }
        if (c != null && c.axes[d] < min.axes[d]) {
            min = c;
        }

        return min;
    }

    public void copyNode(NodeService n1, NodeService n2) {
        for (int i = 0; i <= k; i++) {
            n1 = n2;
        }
    }

    public NodeService deleteNode(NodeService root, long[] axes) {
        return deleteNodeRec(root, axes, 0);
    }

    public NodeService deleteNodeRec(NodeService root, long[] axes, int depth) {
        if (root == null) {
            return null;
        }

        int cd = depth % k;

        if (areaxesSame(root.axes, axes)) {
            if (root.right != null) { // find min in right sub tree
                NodeService min = findMin(root.right, cd);
                copyNode(root, min);
                root.right = deleteNodeRec(root.right, min.axes, depth + 1);
            } else if (root.left != null) { // find min in left sub tree
                NodeService min = findMin(root.left, cd);
                copyNode(root, min);
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

    public NodeService searchNode(NodeService root, NodeService best, long[] axes, int depth, PriorityQueue<NodeService> q) {
        root.idx = 2; // it means leaf
        root.d = (long) Math.pow((double) root.axes[0] - (double) axes[0], 2)
                + (long) Math.pow((double) root.axes[1] - (double) axes[1], 2);
        q.add(root);

        if (best.d > root.d) { // root -> best
            best = root;
        }

        if (root.d == 0) {
            return best;
        }

        int cd = depth % k;

        // move to the next node
        if (axes[cd] < root.axes[cd]) { // left
            if (root.left == null) { // current NodeService != leaf
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

    public NodeService nearest(NodeService root, long[] axes) {
        NodeService best = newNode(new long[]{0, 0});
        PriorityQueue<NodeService> q = new PriorityQueue<>();

        // reset
        return nearestNeighbor(root, best, axes, 0, q);
    }

    public NodeService nearestNeighbor(NodeService root, NodeService best, long[] axes, int depth, PriorityQueue<NodeService> q) {
        int cd = depth % k;

        best = searchNode(root, best, axes, depth, q); // search로 들어가면서 axes와의 distance를 계산해 저장해 그것을 기반으로 queue에 저장

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

    public ArrayList<NodeService> findGroup(NodeService root, NodeService best, NodeService s) {
        if ((best != null) && (best.state <= 2)) {
            best.state++;
            best.group.add(s);
            if (best.state == 1) {
                deleteNode(root, best.axes);
                return best.group;
            }

        }
        return null;
    }
}