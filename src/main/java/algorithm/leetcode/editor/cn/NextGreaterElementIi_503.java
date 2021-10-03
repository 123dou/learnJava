package algorithm.leetcode.editor.cn;
//给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第
//一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。 
//
// 示例 1: 
//
// 
//输入: [1,2,1]
//输出: [2,-1,2]
//解释: 第一个 1 的下一个更大的数是 2；
//数字 2 找不到下一个更大的数； 
//第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
// 
//
// 注意: 输入数组的长度不会超过 10000。 
// Related Topics 栈 数组 单调栈 
// 👍 464 👎 0


import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Arrays;

public class NextGreaterElementIi_503 {
    @Test
    public void test(String[] args) {
        Solution solution = new Solution();
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] nextGreaterElements2(int[] nums) {
        int[] temp = new int[2 * nums.length];
        System.arraycopy(nums, 0, temp, 0, nums.length);

        System.arraycopy(nums, 0, temp, nums.length, nums.length);

        int[] res = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            res[i] = -1;
            for (int j = i + 1; j < temp.length; j++) {
                if (temp[j] > nums[i]) {
                    res[i] = temp[j];
                    break;
                }
            }
        }
        return res;
    }
        public int[] nextGreaterElements(int[] nums) {
            int[] res = new int[nums.length];
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            Arrays.fill(res, -1);
            for (int i = 0; i < nums.length * 2 - 1; i++) {
                int realIdx = i % nums.length;
                while (!stack.isEmpty() && nums[stack.peekLast()] < nums[realIdx]) {
                    Integer idx = stack.pollLast();
                    res[idx] = nums[realIdx];
                }
                stack.addLast(realIdx);
            }
            return res;
        }
}
//leetcode submit region end(Prohibit modification and deletion)

}

