package algorithm.leetcode.editor.cn;
//给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。 
//
// 求在该柱状图中，能够勾勒出来的矩形的最大面积。 
//
// 
//
// 示例 1: 
//
// 
//
// 
//输入：heights = [2,1,5,6,2,3]
//输出：10
//解释：最大的矩形为图中红色区域，面积为 10
// 
//
// 示例 2： 
//
// 
//
// 
//输入： heights = [2,4]
//输出： 4 
//
// 
//
// 提示： 
//
// 
// 1 <= heights.length <=105 
// 0 <= heights[i] <= 104 
// 
// Related Topics 栈 数组 单调栈 
// 👍 1472 👎 0


import algorithm.algs4.sorting.HeapSorting;
import org.junit.Test;

import java.util.ArrayDeque;

public class LargestRectangleInHistogram_84 {
    @Test
    public void test(String[] args) {
        int[] nums = new int[]{2, 1, 5, 6, 2, 3};
        Solution solution = new Solution();
        System.out.println(solution.largestRectangleArea(nums));
    }
    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int largestRectangleArea(int[] heights) {
            if (heights == null || heights.length == 0) {
                return 0;
            }
            int[] temp = new int[heights.length + 2];
            System.arraycopy(heights, 0, temp, 1, heights.length);
            heights = temp;
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            stack.addLast(0);
            int res = 0;
            for (int i = 1; i < heights.length; i++) {
                while (heights[stack.peekLast()] > heights[i]) {
                    int heightIdx = stack.removeLast();
                    res = Math.max(res, heights[heightIdx] * (i - stack.peekLast() - 1));
                }
                stack.addLast(i);
            }
            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
}


