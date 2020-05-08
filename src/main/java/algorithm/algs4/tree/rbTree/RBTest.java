package algorithm.algs4.tree.rbTree;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RBTest {
    public static void main(String[] args) {
        RBTressAlgs4 my = new RBTressAlgs4();
        RedBlackBST<Integer, Integer> algs4RBT = new RedBlackBST<>();
        RBT rbt = new RBT();
        Random r = new Random();
        int n = 2000;
        List<Integer> keys = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int rdm = r.nextInt(n);
            my.put(rdm);
            rbt.add(rdm);
            algs4RBT.put(rdm, rdm);
            keys.add(rdm);
        }
        List<RBTressAlgs4.Node> nodes = my.levelOrder();
        List<RBT.RBNode> nodes2 = rbt.levelOrder();
        List<RedBlackBST<Integer, Integer>.Node> algsNodes = algs4RBT.levelOrder();
        if (!check(algsNodes, nodes)) System.out.println(false);
        else System.out.println(true);
//        if (!check2(algsNodes, nodes2)) System.out.println(false);
        for (int i = 0; i < keys.size(); ) {
            int random = r.nextInt(keys.size());
            my.delete(keys.get(random));
            rbt.delete(keys.get(random));
            algs4RBT.delete(keys.get(random));
            keys.remove(random);
            algsNodes = algs4RBT.levelOrder();
            nodes = my.levelOrder();
            if (!check(algsNodes, nodes)) {
                System.out.println(false);
                return;
            }
        }
        System.out.println(true);
    }

    private static boolean check2(List<RedBlackBST<Integer, Integer>.Node> nodes2, List<RBT.RBNode> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            RBT.RBNode node = nodes.get(i);
            RedBlackBST<Integer, Integer>.Node node2 = nodes2.get(i);
            if (node.key != node2.key || (!node.color) != node2.color)
                return false;
        }
        return true;
    }

    private static boolean check(List<RedBlackBST<Integer, Integer>.Node> nodes2, List<RBTressAlgs4.Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            RBTressAlgs4.Node node = nodes.get(i);
            RedBlackBST<Integer, Integer>.Node node2 = nodes2.get(i);
            if (node.key != node2.key || node.color != node2.color)
                return false;
        }
        return true;
    }


    private static void checkAddAndDelete() {
        RBTree2 my = new RBTree2();
        my.add(12);
        my.add(1);
        my.add(9);
        my.add(2);
        my.add(0);
        my.add(11);
        my.add(7);
        my.add(19);
        my.add(4);
        my.add(15);
        my.add(18);
        my.add(5);
        my.add(14);
        my.add(13);
        my.add(10);
        my.add(16);
        my.add(6);
        my.add(3);
        my.add(8);
        my.add(17);

        List<RBTree2.RBNode> nodes;
        nodes = my.levelOrder();
        System.out.println(nodes);

        my.delete(12);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(1);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(9);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(2);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(0);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(11);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(7);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(19);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(4);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(15);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(18);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(5);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(14);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(13);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(10);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(16);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(6);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(3);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(8);
        nodes = my.levelOrder();
        System.out.println(nodes);
        my.delete(17);
        nodes = my.levelOrder();
        System.out.println(nodes);

    }

}
