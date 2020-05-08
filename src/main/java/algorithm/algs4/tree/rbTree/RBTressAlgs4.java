package algorithm.algs4.tree.rbTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class RBTressAlgs4 {
    private Node root;
    private int size;
    private final boolean RED = true;
    private final boolean BLACK = false;

    public List<Node> levelOrder() {
        List<Node> nodes = new ArrayList<>();
        if (root == null) return nodes;
        Deque<Node> q = new ArrayDeque<>();
        Node t = root;
        q.add(t);
        while (!q.isEmpty()) {
            t = q.poll();
            nodes.add(t);
            if (t.left != null) q.add(t.left);
            if (t.right != null) q.add(t.right);
        }
        return nodes;
    }

    public Node getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public boolean isContain(int key) {
        return isEixt(root, key);
    }

    public boolean isEixt(Node node, int key) {
        if (node == null) return false;
        if (key < node.key) return isEixt(node.left, key);
        else if (key > node.key) return isEixt(node.right, key);
        return true;
    }

    public void put(int key) {
        if (isContain(key)) return;
        root = put(root, key);
        root.color = BLACK;
        size++;
    }

    private Node put(Node node, int key) {
        if (node == null) return new Node(1, key, RED);
        if (key < node.key) node.left = put(node.left, key);
        else node.right = put(node.right, key);

        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColor(node);

        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void deleteMin() {
        if (root == null) return;
        if (isRed(root.left) && isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return null;
        //左子节点为2节点,要向兄弟节点"借一个节点",或者父节点由3-节点或者4-节点变成2-节点或3-节点
        if (!isRed(node.left) && !isRed(node.left.left)) node = moveRedLeft(node);
        node.left = deleteMin(node.left);
        return banlance(node);
    }

    public void deleteMax() {
        if (root == null) return;
        if (isRed(root.left) && isRed(root.right)) root.color = RED;
        root = deletemax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deletemax(Node node) {
        //如果要删除的节点是3-节点,不能直接删除,要先右旋一下,否则会把整个3-节点删除,但实际上我们只需要删除3-节点中较大的节点
        if (isRed(node.left)) node = rotateRight(node);
        if (node.right == null) return null;
        //右节点是2-节点,将其变为3-节点或者4-节点
        if (!isRed(node.right) && !isRed(node.right.left)) node = moveRedRight(node);
        node.right = deletemax(node.right);
        return banlance(node);
    }

    public void delete(int key) {
        if (!isContain(key)) return;
        if (isRed(root.left) && isRed(root.right)) root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        size--;
    }

    /**
     * 如果node.key == key, 则要删除该node
     *
     * @param node
     * @param key
     * @return
     */
    private Node delete(Node node, int key) {
        if (key < node.key) {
            //红黑树的只有红连接并且所有路径上的黑连接数量相同,保证了moveRedLeft(node)不会出现空指针异常
            if (!isRed(node.left) && !isRed(node.left.left)) node = moveRedLeft(node);
            node.left = delete(node.left, key);
        } else {
            if (isRed(node.left)) node = rotateRight(node);
            //要删除的节点没有子节点,经过前面一步已经保证了该节点的子节点不是红接连,根据所有路径上的黑连接数量相同,如果node.right==null,则node.left==null也成立
            if (node.right == null) return null;
            if (!isRed(node.right) && !isRed(node.right.left)) node = moveRedRight(node);
            if (key == node.key) { //要删除的节点有子节点
                node.key = min(node.right).key; //寻找要删除节点的后继节点
                node.right = deleteMin(node.right);
            } else node.right = delete(node.right, key);
        }
        return banlance(node);
    }

    /**
     * 向下将左边的2节点变为三节点
     * 假设node为红节点, node.left, node.right 为黑节点
     * 将左子节点变成3-节点
     *
     * @param node
     * @return
     */
    private Node moveRedLeft(Node node) {
        flipColor(node); //构造一个4-节点
        if (isRed(node.right.left)) { //兄弟节点为3-节点,"借一个节点"
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColor(node);
        }
        return node;
    }

    /**
     * 假设node节点为红节点, node.right, node.right.left为黑节点
     * 将右子节点变成3-节点
     *
     * @param node
     * @return
     */
    private Node moveRedRight(Node node) {
        flipColor(node); //构造一个4-节点
        if (isRed(node.left.left)) { //兄弟节点为3-节点,"借一个节点"
            node = rotateRight(node);
            flipColor(node);
        }
        return node;
    }

    /**
     * 删除操作完成之后向上调整临时的4-节点
     *
     * @param node
     * @return
     */
    private Node banlance(Node node) {
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColor(node);
        return node;
    }


    /**
     * 获取node的最小子节点
     *
     * @param node
     * @return
     */
    private Node min(Node node) {
        if (node.left == null) return node;
        return min(node.left);
    }

    /**
     * 正确的红黑树步骤会保证这个函数不会出现空指针异常
     * 颜色反转,向上传递红连接, 或者向下生成3-节点
     *
     * @param node
     */
    private void flipColor(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    /**
     * /  这个连接可以是左或者右                        /
     * node          ---------->                    x
     * /   \ 红                                 红 /   \
     * node.left   x                                node
     * /  \                                          /    \
     * x.left                                   node.left   x.left
     * <p>
     * 左旋转
     *
     * @param node
     * @return
     */
    private Node rotateLeft(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        x.N = node.N;
        node.N = 1 + size(node.left) + size(node.right);
        return x;
    }

    /**
     * /    这个连接可以是左或者右          /
     * node                                  x
     * 红 /      \          ----->              /   \ 红
     * x        node.right                         node
     * /   \       /  \                              /     \
     * x.right                               x.right    node.right
     * <p>
     * 右旋转
     *
     * @param node
     * @return
     */
    private Node rotateRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        x.N = node.N;
        node.N = 1 + size(node.left) + size(node.right);
        return x;
    }

    /**
     * 判断node的颜色是不是红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == RED;
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.N;
    }

    class Node {
        int N;
        int key;
        Node left;
        Node right;
        boolean color;  //表示父节点指向当前节点的连接的颜色

        Node(int N, int key, boolean color) {
            this.N = N;
            this.key = key;
            this.color = color;
        }


        @Override
        public String toString() {
            return key + " : " + color;
        }
    }
}
