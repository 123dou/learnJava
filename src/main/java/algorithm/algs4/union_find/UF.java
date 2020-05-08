package algorithm.algs4.union_find;

/**
 * 并查集
 */
public abstract class UF {
    protected int[] id; //表示所有的分量
    protected int count;

    /**
     * 以整数标识(0-N-1）初始化N个触点
     * @param N
     */
    public UF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;
    }

    /**
     * 获取连通分量的数量
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * 判断两个触点是否在同一分量
     * @param p
     * @param q
     * @return
     */
    public boolean connect(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * 查找该触点所在的分量标识
     * @param p
     * @return
     */
    public abstract int find(int p);

    /**
     * 连接两个分量
     * @param p
     * @param q
     */
    public abstract void union(int p, int q);
}
