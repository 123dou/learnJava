package algorithm.leetcode.editor.cn;
//在本问题中，有根树指满足以下条件的 有向 图。该树只有一个根节点，所有其他节点都是该根节点的后继。该树除了根节点之外的每一个节点都有且只有一个父节点，而根节
//点没有父节点。 
//
// 输入一个有向图，该图由一个有着 n 个节点（节点值不重复，从 1 到 n）的树及一条附加的有向边构成。附加的边包含在 1 到 n 中的两个不同顶点间，这条
//附加的边不属于树中已存在的边。 
//
// 结果图是一个以边组成的二维数组 edges 。 每个元素是一对 [ui, vi]，用以表示 有向 图中连接顶点 ui 和顶点 vi 的边，其中 ui 是 
//vi 的一个父节点。 
//
// 返回一条能删除的边，使得剩下的图是有 n 个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：edges = [[1,2],[1,3],[2,3]]
//输出：[2,3]
// 
//
// 示例 2： 
//
// 
//输入：edges = [[1,2],[2,3],[3,4],[4,1],[1,5]]
//输出：[4,1]
// 
//
// 
//
// 提示： 
//
// 
// n == edges.length 
// 3 <= n <= 1000 
// edges[i].length == 2 
// 1 <= ui, vi <= n 
// 
// Related Topics 树 深度优先搜索 并查集 图 
// 👍 237 👎 0


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RedundantConnectionIi_685 {
    public static void main(String[] args) {
        Solution4 solution = new Solution4();
//        int[][] edge = {{5, 2}, {1, 2}, {2, 3}, {3, 4}, {4, 5},};
        int[][] edge = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 2}};
        System.out.println(Arrays.toString(solution.findRedundantDirectedConnection(edge)));
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution4 {
    private int[] uf;

    public int[] findRedundantDirectedConnection(int[][] edges) {
        uf = new int[edges.length + 1];
        for (int i = 0; i < uf.length; i++) {
            uf[i] = i;
        }
        int[] parent = Arrays.copyOf(uf, uf.length);
        int conflict = -1;
        int cycle = -1;
        for (int i = 0; i < edges.length; i++) {
            int v = edges[i][0];
            int w = edges[i][1];
            if (parent[w] != w) {
                // 如果存在环，并且这一条边在环里，由于没有进行union，所以后面一定不会检查到存在环；
                // 如果存在环，并且这一条边不在环里，则在环里的就一定是new int[]{parent[edges[conflict][1]], edges[conflict][1]}
                conflict = i;
            } else {
                parent[w] = v;
                if (!isConn(v, w)) {
                    union(v, w);
                } else {
                    cycle = i;
                }
            }

        }
        if (conflict != -1) {
            if (cycle == -1) {
                return edges[conflict];
            }
            return new int[]{parent[edges[conflict][1]], edges[conflict][1]};
        }
        return edges[cycle];
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
        uf[wRoot] = vRoot;
    }


    Set<Integer>[] adjs;
    Set<Integer> cycle;
    int[] cycleStack;
    int[] inDegree;

    //1.如果没有入度为2的顶点，则答案为环中的任意一条边
    //2.如果存在入度为2的顶点，则答案一定为入度为2的顶点里面一条边：如果存在环则是入度为2的顶点并且在环里那一条边否则就是任意一条边
    public int[] findRedundantDirectedConnection2(int[][] edges) {
        if (edges == null) {
            return new int[0];
        }
        int max = edges.length + 1;
        adjs = new Set[max + 1];
        cycleStack = new int[max + 1];
        inDegree = new int[max + 1];
        for (int i = 0; i < adjs.length; i++) {
            adjs[i] = new HashSet<>();
            cycleStack[i] = i;
        }
        int degree2 = -1;
        for (int[] edge : edges) {
            add(edge[0], edge[1]);
            inDegree[edge[1]]++;
            if (inDegree[edge[1]] == 2) {
                degree2 = edge[1];
            }
        }
        cycle = new HashSet<>();
        boolean[] marked = new boolean[max + 1];
        for (int i = 1; i < edges.length; i++) {
            if (!marked[i]) {
                if (findCycle(i, marked)) {
                    break;
                }
            }
        }
        for (int i = edges.length - 1; i >= 0; i--) {
            int u = edges[i][0];
            int v = edges[i][1];
            if (cycle.size() != 0) {
                if (cycle.contains(u) && cycle.contains(v) && adjs[u].contains(v) && (degree2 == -1 || v == degree2)) {
                    return edges[i];
                }
            } else {
                if (inDegree[v] == 2) {
                    return edges[i];
                }
            }
        }
//        for (int i = edges.length - 1; i >= 0; i--) {
//            int v = edges[i][1];
//            if (inDegree[v] == 2) {
//                return edges[i];
//            }
//        }
        return new int[0];
    }

    private boolean findCycle(int v, boolean[] marked) {
        marked[v] = true;
        for (Integer w : adjs[v]) {
            if (!marked[w]) {
                cycleStack[w] = v;
                if (findCycle(w, marked)) {
                    return true;
                }
            } else {
                cycleStack[w] = w;
                cycle.add(w);
                for (int i = v; i != cycleStack[i]; i = cycleStack[i]) {
                    cycle.add(i);
                }
                return true;
            }
        }
        marked[v] = false;
        return false;
    }

    void add(int v, int w) {
        adjs[v].add(w);
    }

}
//leetcode submit region end(Prohibit modification and deletion)
