package algorithm.leetcode.editor.cn;
//给定一个机票的字符串二维数组 [from, to]，子数组中的两个成员分别表示飞机出发和降落的机场地点，对该行程进行重新规划排序。所有这些机票都属于一个从 
//JFK（肯尼迪国际机场）出发的先生，所以该行程必须从 JFK 开始。 
//
// 
//
// 提示： 
//
// 
// 如果存在多种有效的行程，请你按字符自然排序返回最小的行程组合。例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序
//更靠前 
// 所有的机场都用三个大写字母表示（机场代码）。 
// 假定所有机票至少存在一种合理的行程。 
// 所有的机票必须都用一次 且 只能用一次。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：[["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
//输出：["JFK", "MUC", "LHR", "SFO", "SJC"]
// 
//
// 示例 2： 
//
// 
//输入：[["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
//输出：["JFK","ATL","JFK","SFO","ATL","SFO"]
//解释：另一种有效的行程是 ["JFK","SFO","ATL","JFK","ATL","SFO"]。但是它自然排序更大更靠后。 
// Related Topics 深度优先搜索 图 
// 👍 384 👎 0


import java.util.*;

public class ReconstructItinerary_332 {
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("EZE", "AXA"));
        lists.add(Arrays.asList("TIA", "ANU"));
        lists.add(Arrays.asList("ANU", "JFK"));
        lists.add(Arrays.asList("JFK", "ANU"));
        lists.add(Arrays.asList("ANU", "EZE"));
        lists.add(Arrays.asList("TIA", "ANU"));
        lists.add(Arrays.asList("AXA", "TIA"));
        lists.add(Arrays.asList("TIA", "JFK"));
        lists.add(Arrays.asList("ANU", "TIA"));
        lists.add(Arrays.asList("JFK", "TIA"));
        List<String> res = solution.findItinerary(lists);
        System.out.println(res);
    }
}

//leetcode submit region begin(Prohibit modification and deletion)
class Solution1 {
    Map<String, PriorityQueue<String>> map = new HashMap<String, PriorityQueue<String>>();
    List<String> itinerary = new LinkedList<>();

    public List<String> findItinerary(List<List<String>> tickets) {
        for (List<String> ticket : tickets) {
            String src = ticket.get(0), dst = ticket.get(1);
            map.computeIfAbsent(src, key -> new PriorityQueue<>()).add(dst);
        }
        dfs("JFK");
        Collections.reverse(itinerary);
        return itinerary;
    }

    public void dfs(String curr) {
        while (map.containsKey(curr) && map.get(curr).size() > 0) {
            String tmp = map.get(curr).poll();
            dfs(tmp);
        }
        itinerary.add(curr);
    }
}


//leetcode submit region end(Prohibit modification and deletion)
