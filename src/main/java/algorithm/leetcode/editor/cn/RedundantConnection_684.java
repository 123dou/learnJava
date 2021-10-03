package algorithm.leetcode.editor.cn;
//在本问题中, 树指的是一个连通且无环的无向图。 
//
// 输入一个图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。附加的边的两个顶点包含在1到N中间，这条附加的边不属
//于树中已存在的边。 
//
// 结果图是一个以边组成的二维数组。每一个边的元素是一对[u, v] ，满足 u < v，表示连接顶点u 和v的无向图的边。 
//
// 返回一条可以删去的边，使得结果图是一个有着N个节点的树。如果有多个答案，则返回二维数组中最后出现的边。答案边 [u, v] 应满足相同的格式 u < v。
// 
//
// 示例 1： 
//
// 输入: [[1,2], [1,3], [2,3]]
//输出: [2,3]
//解释: 给定的无向图为:
//  1
// / \
//2 - 3
// 
//
// 示例 2： 
//
// 输入: [[1,2], [2,3], [3,4], [1,4], [1,5]]
//输出: [1,4]
//解释: 给定的无向图为:
//5 - 1 - 2
//    |   |
//    4 - 3
// 
//
// 注意: 
//
// 
// 输入的二维数组大小在 3 到 1000。 
// 二维数组中的整数在1到N之间，其中N是输入数组的大小。 
// 
//
// 更新(2017-09-26): 
//我们已经重新检查了问题描述及测试用例，明确图是无向 图。对于有向图详见冗余连接II。对于造成任何不便，我们深感歉意。 
// Related Topics 树 并查集 图 
// 👍 334 👎 0


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RedundantConnection_684 {
    public static void main(String[] args) {
        Solution3 solution3 = new Solution3();
        int[][] edge = {{2, 7}, {7, 8}, {3, 6}, {2, 5}, {6, 8}, {4, 8}, {2, 8}, {1, 8}, {7, 10}, {3, 9}};
        System.out.println(Arrays.toString(solution3.findRedundantConnection(edge)));
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution3 {






    private int[] uf;
    private int[] size;

    public int[] findRedundantConnection(int[][] edges) {
        uf = new int[edges.length + 1];
        for (int i = 0; i < uf.length; i++) {
            uf[i] = i;
        }
        size = Arrays.copyOf(uf, uf.length);
        for (int[] edge : edges) {
            int v = edge[0];
            int w = edge[1];
            if (!isConn(v, w)) {
                union(v, w);
            } else {
                return edge;
            }
        }
        return new int[0];
    }

    boolean isConn(int v, int w) {
        return find(v) == find(w);
    }

    int find(int v) {
        while (uf[v] != v) {
            v = uf[v];
        }
        return v;
    }

    void union(int v, int w) {
        int vRoot = find(v);
        int wRoot = find(w);
        if (vRoot == wRoot) {
            return;
        }
        if (size[vRoot] < size[wRoot]) {
            uf[vRoot] = wRoot;
            size[wRoot] += size[vRoot];
        } else {
            uf[wRoot] = vRoot;
            size[vRoot] += size[wRoot];
        }
    }


    Set<Integer>[] adjs;
    Set<Integer> cycle;
    int[] cycleStack;

    public int[] findRedundantConnection2(int[][] edges) {
        if (edges == null) {
            return new int[0];
        }
        int max = edges.length + 1;
//        for (int[] edge : edges) {
//             由题目得edge[0] < edge[1]
//            max = Math.max(max, Math.max(edge[0], edge[1]));
//        }
        adjs = new Set[max + 1];
        cycleStack = new int[max + 1];
        for (int i = 0; i < adjs.length; i++) {
            adjs[i] = new HashSet<>();
            cycleStack[i] = i;
        }
        for (int[] edge : edges) {
            add(edge[0], edge[1]);
        }
        cycle = new HashSet<>();
        boolean[] marked = new boolean[max + 1];
        for (int i = 0; i < edges.length; i++) {
            if (!marked[i]) {
                if (findCycle(i, i, marked)) {
                    break;
                }
            }
        }
        for (int i = edges.length - 1; i >= 0; i--) {
            int u = edges[i][0];
            int v = edges[i][1];
            if (cycle.contains(u) && cycle.contains(v) && adjs[u].contains(v)) {
                return edges[i];
            }
        }
        return new int[0];
    }

    // 只适用与连通图
    private boolean findCycle(int front, int v, boolean[] marked) {
        marked[v] = true;
        for (Integer w : adjs[v]) {
            if (!marked[w]) {
                cycleStack[w] = v;
                if (findCycle(v, w, marked)) {
                    return true;
                }
            } else if (front != w) {
                cycleStack[w] = w;
                cycle.add(w);
                for (int i = v; i != cycleStack[i]; i = cycleStack[i]) {
                    cycle.add(i);
                }
                return true;
            }
        }
        return false;
    }

    void add(int v, int w) {
        adjs[v].add(w);
        adjs[w].add(v);
    }

}
//leetcode submit region end(Prohibit modification and deletion)
