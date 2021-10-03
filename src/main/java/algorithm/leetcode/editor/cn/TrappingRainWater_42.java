package algorithm.leetcode.editor.cn;
//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
//输出：6
//解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 
// 
//
// 示例 2： 
//
// 
//输入：height = [4,2,0,3,2,5]
//输出：9
// 
//
// 
//
// 提示： 
//
// 
// n == height.length 
// 0 <= n <= 3 * 104 
// 0 <= height[i] <= 105 
// 
// Related Topics 栈 数组 双指针 动态规划 单调栈 
// 👍 2580 👎 0


import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;

public class TrappingRainWater_42 {
    @Test
    public void test() {
        Solution solution = new Solution();
        int[] h = new int[]{4, 2, 0, 3, 2, 5};
        int trap = solution.trap(h);
        System.out.println(trap);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        // 单调栈的解法
        public int trap1(int[] height) {
            int ans = 0;
            Deque<Integer> stack = new LinkedList<Integer>();
            int n = height.length;
            for (int i = 0; i < n; ++i) {
                while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                    int top = stack.pop();
                    if (stack.isEmpty()) {
                        break;
                    }
                    int left = stack.peek();
                    int currWidth = i - left - 1;
                    int currHeight = Math.min(height[left], height[i]) - height[top];
                    ans += currWidth * currHeight;
                }
                stack.push(i);
            }
            return ans;
        }

        // 动态规划的解法，按列来求
        public int trap2(int[] height) {
            int len = height.length;
            if (len == 0) {
                return 0;
            }
            int maxLeft = height[0];
            int[] maxRight = new int[len];
            maxRight[len - 1] = height[len - 1];
            for (int i = len - 2; i >= 0; i--) {
                maxRight[i] = Math.max(maxRight[i + 1], height[i]);
            }
            int res = 0;
            for (int i = 1; i < len; i++) {
                maxLeft = Math.max(height[i], maxLeft);
                int min = Math.min(maxLeft, maxRight[i]);
                if (min > height[i]) {
                    res += min - height[i];
                }
            }
            return res;
        }

        public int trap(int[] h) {
            int len = h.length;
            if (len == 0) {
                return 0;
            }
            int left = 1;
            int right = len - 2;
            int maxLeft = 0;
            int maxRight = 0;
            int res = 0;
            for (int i = 1; i < len - 1; i++) {
                if (h[left - 1] < h[right + 1]) {
                    maxLeft = Math.max(maxLeft, h[left - 1]);
                    int min = maxLeft;
                    if (min > h[left]) {
                        res += min - h[left];
                    }
                    left++;
                } else {
                    maxRight = Math.max(maxRight, h[right + 1]);
                    int min = maxRight;
                    if (min > h[right]) {
                        res += min - h[right];
                    }
                    right--;
                }
            }
            return res;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}

