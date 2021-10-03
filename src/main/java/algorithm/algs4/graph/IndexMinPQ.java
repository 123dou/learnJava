package algorithm.algs4.graph;


import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int n; //队列中元素的最后位置
    private int[] que; //优先队列,实际比较的是keys[pq[i]]
    private int[] map; //key 到优先队列索引的映射
    private Key[] keys; //实际存储的对像
    private final int CAPACITY; //优先队列的容量

    public IndexMinPQ(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException();
        que = new int[capacity + 1];
        map = new int[capacity + 1];
        keys = (Key[]) new Comparable[capacity + 1];
        Arrays.fill(map, -1);
        CAPACITY = capacity;
    }

    public void insert(int k, Key key) {
        if (contains(k))
            throw new IllegalArgumentException("index " + k + "already exist!");
        if (k <= 0 || k > CAPACITY)
            throw new IllegalArgumentException("index : " + k + "out of capacity!");
        que[++n] = k;
        keys[k] = key;
        map[k] = n;
        swim(n);
    }

    /**
     * 删除最小key并返回其关联的索引
     *
     * @return
     */
    public int deleteMin() {
        int min = que[1];
        exch(1, n--);
        sink(1);
        map[min] = -1;
        keys[min] = null;
        return min;
    }

    /**
     * 删示索引为k的key
     *
     * @param k
     */
    public void delete(int k) {
        if (!contains(k))
            throw new IllegalArgumentException("no this index:" + k);
        if (k <= 0 || k > CAPACITY)
            throw new IllegalArgumentException("index : " + k + "out of capacity!");
        int index = map[k];
        exch(index, n--);
        swim(index);
        sink(index);
        map[index] = -1;
        keys[index] = null; //help gc

    }

    /**
     * 返回最小的key
     *
     * @return
     */
    public Key min() {
        return keys[que[0]];
    }

    /**
     * 返回最小key的索引
     *
     * @return
     */
    public int MinIndex() {
        return que[1];
    }

    /**
     * @return 队列中元素的数量
     */
    public int size() {
        return n;
    }

    /**
     * 改变该索引关联的key
     *
     * @param k
     * @param key
     */
    public void change(int k, Key key) {
        chanegeKey(k, key);
    }

    private void chanegeKey(int k, Key key) {
        if (!contains(k))
            throw new IllegalArgumentException("no this index:" + k);
        if (k <= 0 || k > CAPACITY)
            throw new IllegalArgumentException("index : " + k + "out of capacity!");
        keys[k] = key;
        swim(map[k]);
        sink(map[k]);
    }


    /**
     * 向上调整队列
     *
     * @param k
     */
    private void swim(int k) {
        while (k > 1) {
            int parent = k / 2;
            if (less(k, parent)) exch(k, parent);
            k = parent;
        }
    }

    /**
     * 向下调整队列
     *
     * @param k
     */
    private void sink(int k) {
        while (2 * k <= n) {
            int t = 2 * k;
            if (t + 1 <= n && less(t + 1, t)) t++;
            if (!less(k, t)) break;
            exch(k, t);
            k = t;
        }
    }

    /**
     * 交换两个pq,qp,keys中对应的i, j
     *
     * @param i
     * @param j
     */
    private void exch(int i, int j) {
        int t = que[i];
        que[i] = que[j];
        que[j] = t;
        map[que[i]] = i;
        map[que[j]] = j;
    }

    /**
     * 比较两个索引对应的key的大小
     * 若i对应的key小于j对应的key则返回true
     *
     * @param i
     * @param j
     * @return
     */
    private boolean less(int i, int j) {
        return keys[que[i]].compareTo(keys[que[j]]) < 0;

    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(int k) {
        if (k > CAPACITY || k <= 0)
            throw new IllegalArgumentException("out of priority capacity: " + k);
        return map[k] != -1;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        private IndexMinPQ<Key> copy;

        public HeapIterator() {
            copy = new IndexMinPQ<>(que.length - 1);
            for (int i = 1; i < que.length; i++) copy.insert(que[i], keys[que[i]]);
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer next() {
            return copy.deleteMin();
        }
    }
}
