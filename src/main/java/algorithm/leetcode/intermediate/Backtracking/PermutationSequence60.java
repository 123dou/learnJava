package algorithm.leetcode.intermediate.Backtracking;

import java.util.Arrays;

/**
 * ### [60\. 第k个排列](https://leetcode-cn.com/problems/permutation-sequence/)
 * <p>
 * 难度 **中等**
 * <p>
 * 给出集合 `[1,2,3,…,_n_]`，其所有元素共有 _n_! 种排列。
 * <p>
 * 按大小顺序列出所有排列情况，并一一标记，当 _n_ = 3 时, 所有排列如下：
 * <p>
 * 给定 _n_ 和 _k_，返回第 _k_ 个排列。
 * <p>
 * *说明：**
 * <p>
 * *示例 1:**
 * <p>
 * *示例 2:**
 * <p>
 * ```
 * 输入: n = 3, k = 3
 * 输出: "213"
 * 输入: n = 4, k = 9
 * 输出: "2314"
 * ```
 * <p>
 * #### Solution
 * <p>
 * Language: **Java**
 * <p>
 * ```
 **/
public class PermutationSequence60 {
    public static void main(String[] args) {
        PermutationSequence60 t = new PermutationSequence60();
        int n = 3;
        int k = 2;
        String s = t.getPermutation(n, k);
        System.out.println(s);
    }

    //思路：逐个确定位置
    public String getPermutation(int n, int k) {
        if (n == 1) return "1";
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        int num = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int fac = factorial(n - i - 1);
                num += fac;
                if (num == k) break;
                if (num > k) {
                    if (num - fac != 0) k -= (num - fac);
                    num = 0;
                    break;
                } else {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            if (num == k) {
                //用lambda表达式慢很多
                //Arrays.sort(arr, i + 1, arr.length, (a, b) -> -a + b);
                sort(arr, i+1, arr.length);
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i : arr) sb.append(i);
        return sb.toString();
    }

    public void sort(Integer[] arr, int fromIndex, int toIndex) {
        for (int i = fromIndex + 1; i < toIndex; i++) {
            int pre = i - 1;
            int val = arr[i];
            while (pre >= fromIndex && arr[pre] < val) {
                arr[pre + 1] = arr[pre];
                pre--;
            }
            arr[pre + 1] = val;
        }
    }

    private int factorial(int i) {
        int res = 1;
        for (; i > 0; i--) res *= i;
        return res;
    }
}
