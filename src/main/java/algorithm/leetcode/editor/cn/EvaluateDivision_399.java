package algorithm.leetcode.editor.cn;
//给你一个变量对数组 equations 和一个实数值数组 values 作为已知条件，其中 equations[i] = [Ai, Bi] 和 values
//[i] 共同表示等式 Ai / Bi = values[i] 。每个 Ai 或 Bi 是一个表示单个变量的字符串。 
//
// 另有一些以数组 queries 表示的问题，其中 queries[j] = [Cj, Dj] 表示第 j 个问题，请你根据已知条件找出 Cj / Dj =
// ? 的结果作为答案。 
//
// 返回 所有问题的答案 。如果存在某个无法确定的答案，则用 -1.0 替代这个答案。如果问题中出现了给定的已知条件中没有出现的字符串，也需要用 -1.0 替
//代这个答案。 
//
// 注意：输入总是有效的。你可以假设除法运算中不会出现除数为 0 的情况，且不存在任何矛盾的结果。 
//
// 
//
// 示例 1： 
//
// 
//输入：equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"]
//,["b","a"],["a","e"],["a","a"],["x","x"]]
//输出：[6.00000,0.50000,-1.00000,1.00000,-1.00000]
//解释：
//条件：a / b = 2.0, b / c = 3.0
//问题：a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
//结果：[6.0, 0.5, -1.0, 1.0, -1.0 ]
// 
//
// 示例 2： 
//
// 
//输入：equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], quer
//ies = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
//输出：[3.75000,0.40000,5.00000,0.20000]
// 
//
// 示例 3： 
//
// 
//输入：equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a
//","c"],["x","y"]]
//输出：[0.50000,2.00000,-1.00000,-1.00000]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= equations.length <= 20 
// equations[i].length == 2 
// 1 <= Ai.length, Bi.length <= 5 
// values.length == equations.length 
// 0.0 < values[i] <= 20.0 
// 1 <= queries.length <= 20 
// queries[i].length == 2 
// 1 <= Cj.length, Dj.length <= 5 
// Ai, Bi, Cj, Dj 由小写英文字母与数字组成 
// 
// Related Topics 并查集 图 
// 👍 508 👎 0


import java.util.*;

public class EvaluateDivision_399 {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        List<List<String>> equations = new ArrayList<>();
        equations.add(Arrays.asList("a", "b"));
        equations.add(Arrays.asList("b", "c"));
        double[] arr = new double[]{2.0, 3.0};
        List<List<String>> ques = new ArrayList<>();
        ques.add(Arrays.asList("a", "c"));
        ques.add(Arrays.asList("b", "a"));
        ques.add(Arrays.asList("a", "e"));
        ques.add(Arrays.asList("a", "a"));
        ques.add(Arrays.asList("x", "x"));
        ques.add(Arrays.asList("abx", "cax"));
        System.out.println(Arrays.toString(solution.calcEquation(equations, arr, ques)));
    }
}

/**
 * 简单题，但是没有看清题目
 */
//leetcode submit region begin(Prohibit modification and deletion)
class Solution2 {
    Map<String, Map<String, Double>> adj = new HashMap<>();

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int idx = 0;
        for (List<String> e : equations) {
            String from = e.get(0);
            String to = e.get(1);
            double value = values[idx++];
            adj.computeIfAbsent(from, key -> new HashMap<>()).put(to, value);
            adj.computeIfAbsent(to, Key -> new HashMap<>()).put(from, 1 / value);
        }
        double[] res = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            res[i] = calc(queries.get(i));
        }
        return res;
    }

    private double calc(List<String> strings) {
        String from = strings.get(0);
        String to = strings.get(1);
        double[] res = new double[]{1};
        if (calc(from, to, new HashSet<>(), adj, res)) {
            return res[0];
        }
        return -1;
    }

    private boolean calc(String from, String to, Set<String> marked, Map<String, Map<String, Double>> adjs, double[] res) {
        if (from.equals(to) && adjs.containsKey(from)) {
            res[0] = 1;
            return true;
        }
        marked.add(from);
        Map<String, Double> edges = adjs.get(from);
        if (edges != null) {
            Double edge = edges.get(to);
            if (edge != null) {
                res[0] *= edge;
                return true;
            }
            for (String fromAdj : edges.keySet()) {
                if (!marked.contains(fromAdj)) {
                    res[0] *= edges.get(fromAdj);
                    if (calc(fromAdj, to, marked, adjs, res)) {
                        return true;
                    }
                    res[0] /= edges.get(fromAdj);
                }
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
