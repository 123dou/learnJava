package algorithm.algs4.graph.directedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 有向图
 */
public class DiGraph {
    private final int VERTEX_NUMS; //顶点的个数
    private int E; //边的数量
    private ArrayList<Integer>[] adj; //邻接表

    public DiGraph(int vertexNums) {
        this.VERTEX_NUMS = vertexNums;
        adj = new ArrayList[vertexNums];
        for (int v = 0; v < vertexNums; v++) adj[v] = new ArrayList<>();
    }

    public DiGraph(Scanner in, int vertexNums) {
        this(vertexNums);
        int e = in.nextInt();
        for (int i = 0; i < e; i++) {
            int v = in.nextInt();
            int w = in.nextInt();
            addEdges(v, w);
        }
    }

    public void addEdges(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public int getVERTEX_NUMS() {
        return VERTEX_NUMS;
    }

    public int getE() {
        return E;
    }

    public List<Integer> adj(int v) {
        return adj[v];
    }

    /**
     * 将图反转
     *
     * @return
     */
    public DiGraph reverse() {
        DiGraph R = new DiGraph(VERTEX_NUMS);
        for (int v = 0; v < VERTEX_NUMS; v++) {
            for (int w : adj(v)) {
                R.adj[w].add(v);
            }
        }
        return R;
    }
}
