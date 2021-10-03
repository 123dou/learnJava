package algorithm.testDemo;


import java.util.*;

class Solution {

    public static void main(String[] args) {
        System.out.println(Objects.hash("123", "456"));
    }
    public List<Integer> findMinHeightTrees(int n, int[][] arr) {
        if (arr == null) {
            return new ArrayList<>();
        }
        Graph graph = new Graph(n, arr);
        ArrayDeque<Integer> que = new ArrayDeque<>();
        for (int i = 0; i < graph.adj.length; i++) {
            if (graph.adj[i].size() == 1) {
                que.add(i);
            }
        }
        List<Integer> list = new ArrayList<>();
        while (!que.isEmpty()) {
            list = new ArrayList<>();
            while (!que.isEmpty()) {
                list.add(que.pollFirst());
            }
            // 删除所有度为1的节点，并且将下一层度为1的节点添加进队列
            for (Integer v : list) {
                if (graph.adj[v].size() > 0) {
                    Integer w = graph.adj[v].get(0);
                    graph.delete(v, w);
                    if (graph.adj[w].size() == 1) {
                        que.add(w);
                    }
                }
            }
        }
        return list;
    }


    class Graph {
        int n;
        ArrayList<Integer>[] adj;

        Graph(int num, int[][] arr) {
            n = num;
            adj = new ArrayList[num];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<>();
            }
            for (int[] e : arr) {
                add(e[0], e[1]);
            }
        }

        void add(int v, int w) {
            adj[v].add(w);
            adj[w].add(v);
        }

        void delete(int v, int w) {
            adj[v].remove(Integer.valueOf(w));
            adj[w].remove(Integer.valueOf(v));
        }

    }
}