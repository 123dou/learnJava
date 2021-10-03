package algorithm.leetcode.editor.cn;
//有 n 个网络节点，标记为 1 到 n。 
//
// 给你一个列表 times，表示信号经过 有向 边的传递时间。 times[i] = (ui, vi, wi)，其中 ui 是源节点，vi 是目标节点， w
//i 是一个信号从源节点传递到目标节点的时间。 
//
// 现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1 。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
//输出：2
// 
//
// 示例 2： 
//
// 
//输入：times = [[1,2,1]], n = 2, k = 1
//输出：1
// 
//
// 示例 3： 
//
// 
//输入：times = [[1,2,1]], n = 2, k = 2
//输出：-1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= k <= n <= 100 
// 1 <= times.length <= 6000 
// times[i].length == 3 
// 1 <= ui, vi <= n 
// ui != vi 
// 0 <= wi <= 100 
// 所有 (ui, vi) 对都 互不相同（即，不含重复边） 
// 
// Related Topics 堆 深度优先搜索 广度优先搜索 图 
// 👍 253 👎 0


import algorithm.algs4.graph.IndexMinPQ;

import java.util.*;

public class NetworkDelayTime_743 {
    public static void main(String[] args) {
        Solution5 solution = new Solution5();
        int[][] arr = new int[][]{
                {2, 1, 8},
                {2, 3, 1},
                {3, 1, 2}
        };
        System.out.println(solution.networkDelayTime2(arr, 2, 2));
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
//最短路径问题
class Solution5 {

    private Set<int[]>[] adjs;
    private int[] distTo;
    private boolean[] onQue;
    private boolean[] marked;
    private IndexMinPQ<Integer> indexQue;
    private ArrayDeque<Integer> que;

    public int networkDelayTime(int[][] times, int n, int k) {
        adjs = new Set[n + 1];
        distTo = new int[n + 1];
        onQue = new boolean[n + 1];
        for (int i = 1; i < adjs.length; i++) {
            adjs[i] = new HashSet<>();
            distTo[i] = Integer.MAX_VALUE;
        }
        for (int[] time : times) {
            int u = time[0];
            int v = time[1];
            int w = time[2];
            adjs[u].add(new int[]{v, w});
        }
        PriorityQueue<Integer> q = new PriorityQueue<>();

        calcShortestPath2(k, n);
        return checkDistTo();
    }

    public int networkDelayTime2(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> graph = new HashMap();
        for (int[] edge : times) {
            if (!graph.containsKey(edge[0])) {
                graph.put(edge[0], new ArrayList<int[]>());
            }
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>(
                Comparator.comparingInt(info -> info[0]));
        heap.offer(new int[]{0, K});

        Map<Integer, Integer> dist = new HashMap();
        while (!heap.isEmpty()) {
            int[] info = heap.poll();
            int d = info[0], node = info[1];
            if (dist.containsKey(node)) {
                continue;
            }
            dist.put(node, d);
            if (graph.containsKey(node))
                for (int[] edge : graph.get(node)) {
                    int nei = edge[0], d2 = edge[1];
                    if (!dist.containsKey(nei)) {
                        heap.offer(new int[]{d + d2, nei});
                    }
                }
        }

        if (dist.size() != N) return -1;
        int ans = 0;
        for (int cand : dist.values()) {
            ans = Math.max(ans, cand);
        }
        return ans;
    }

    private void calcShortestPath2(int k, int vCount) {
        indexQue = new IndexMinPQ<>(vCount + 1);
        distTo[k] = 0;
        indexQue.insert(k, 0);
        while (!indexQue.isEmpty()) {
            int v = indexQue.deleteMin();
            relax2(v);
        }
    }

    private void relax2(int from) {
        for (int[] e : adjs[from]) {
            int to = e[0];
            int w = e[1];
            if (distTo[to] > distTo[from] + w) {
                distTo[to] = distTo[from] + w;
                if (indexQue.contains(to)) {
                    indexQue.change(to, distTo[to]);
                } else {
                    indexQue.insert(to, distTo[to]);
                }
            }
        }
    }

    private void calcShortestPath1(int k) {
        que = new ArrayDeque<>();
        que.add(k);
        distTo[k] = 0;
        onQue[k] = true;
        while (!que.isEmpty()) {
            Integer u = que.pollFirst();
            onQue[u] = false;
            relax(u);
        }
    }

    private int checkDistTo() {
        int res = Arrays.stream(distTo).max().getAsInt();
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private void relax(int u) {
        for (int[] e : adjs[u]) {
            int v = e[0];
            int w = e[1];
            if (distTo[v] > distTo[u] + w) {
                distTo[v] = distTo[u] + w;
                if (!onQue[v]) {
                    que.addLast(v);
                    onQue[v] = true;
                }
            }
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)
